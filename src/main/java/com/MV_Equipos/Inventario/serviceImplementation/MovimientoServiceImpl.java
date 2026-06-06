package com.MV_Equipos.Inventario.serviceImplementation;

import com.MV_Equipos.Inventario.Exception.RecursoNoEncontradoException;
import com.MV_Equipos.Inventario.Exception.ValidacionException;
import com.MV_Equipos.Inventario.entity.Movimiento;
import com.MV_Equipos.Inventario.entity.Producto;
import com.MV_Equipos.Inventario.entity.Usuario;
import com.MV_Equipos.Inventario.enums.TipoMovimiento;
import com.MV_Equipos.Inventario.repository.MovementRepository;
import com.MV_Equipos.Inventario.service.MovimientoService;
import com.MV_Equipos.Inventario.service.ProductoService;
import com.MV_Equipos.Inventario.service.UsuarioService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Service
public class MovimientoServiceImpl implements MovimientoService {

    @Autowired
    private MovementRepository movementRepository;
    @Autowired
    private ProductoService productoService;
    @Autowired
    private UsuarioService usuarioService;



    @Transactional
    @Override
    public Movimiento registrarEntrada(Integer productoId, Integer usuarioId, Integer cantidad, String comentarios, MultipartFile archivo) {
        Producto productoEncontrado = productoService.buscarPorID(productoId);
        Usuario usuarioEncontrado = usuarioService.obtenerPorID(usuarioId);
        String tipoArchivo = null;
        String nombreArchivo = null;
        Path rutaArchivo = null;
        String rutaCarpeta ="uploads/entradas/";



        if (archivo != null && !archivo.isEmpty()) {

            tipoArchivo = archivo.getContentType();



            if (tipoArchivo == null ||(!tipoArchivo.equals("application/pdf") &&
                            !tipoArchivo.equals("application/xml") &&
                            !tipoArchivo.equals("text/xml"))) {

                throw new ValidacionException("Solo se permiten archivos PDF o XML");
            }
            nombreArchivo = archivo.getOriginalFilename();
            rutaArchivo =Paths.get(rutaCarpeta + nombreArchivo);
        }



            Integer cantidadValidada=validarCantidad(cantidad);
            Integer stockAnterior = productoEncontrado.getStock();
            Integer stockFinal = stockAnterior + cantidadValidada;
            productoEncontrado.setStock(stockFinal);
            productoService.guardarProducto(productoEncontrado);
            Movimiento movimiento = Movimiento.builder()//gracias a loombok podemos crear los objetos de forma mas estructurada
                    .productoId(productoEncontrado)
                    .userId(usuarioEncontrado)
                    .tipoMovimiento(TipoMovimiento.ENTRADA)
                    .cantidad(cantidadValidada)
                    .stockFinal(stockFinal)
                    .comentarios(comentarios)
                    .nombreArchivo(nombreArchivo)//Mandara los datos de los atributos que se agregaron para cargar archivos
                    .rutaArchivo(   rutaArchivo != null
                                    ? rutaArchivo.toString()
                                    : null)
                    .tipoArchivo(tipoArchivo)
                    .build();
            if (archivo != null && !archivo.isEmpty()) {

                try {

                    Files.createDirectories(Paths.get(rutaCarpeta));

                    Files.write(rutaArchivo, archivo.getBytes());

                } catch (IOException e) {

                    throw new ValidacionException("Error al guardar el archivo");
                }
            }


            return movementRepository.save(movimiento);



    }



    @Transactional
    @Override
    public Movimiento registrarSalida(Integer productoId, Integer usuarioId, Integer cantidad, String comentarios) {
        Producto productoEncontrado = productoService.buscarPorID(productoId);
        Usuario usuarioEncontrado = usuarioService.obtenerPorID(usuarioId);

            Integer cantidadValidada=validarCantidad(cantidad);
            Integer stockAnterior = productoEncontrado.getStock();
            if(cantidadValidada>stockAnterior){
                throw new ValidacionException("No hay suficiente stock");
            }
            Integer stockFinal = stockAnterior - cantidadValidada;

                productoEncontrado.setStock(stockFinal);
                productoService.guardarProducto(productoEncontrado);
                Movimiento movimiento = Movimiento.builder()
                        .productoId(productoEncontrado)
                        .userId(usuarioEncontrado)
                        .tipoMovimiento(TipoMovimiento.SALIDA)
                        .cantidad(cantidadValidada)
                        .stockFinal(stockFinal)
                        .comentarios(comentarios)
                        .build();
                return movementRepository.save(movimiento);





    }

    @Transactional(readOnly = true)
    @Override
    public List<Movimiento> obtenerMovimientos() {
        return movementRepository.findAll();
    }
    @Transactional(readOnly = true)
    @Override
    public List<Movimiento> obtenerPorID(Integer productoId) {

        validarId(productoId);
        productoService.buscarPorID(productoId);

        return movementRepository.findByProductoId_Id(productoId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Movimiento> obtenerEntradas(TipoMovimiento movimientoEnv) {

        return movementRepository.findByTipoMovimiento(movimientoEnv);
    }

    @Transactional(readOnly = true)
    @Override
    public Movimiento buscarMovimientoPorID(Integer id) {

        validarId(id);
        return movementRepository.findById(id).orElseThrow(()->new RecursoNoEncontradoException("Movimiento no encontrado"));
    }

    @Transactional(readOnly = true)
    @Override
    public Resource obtenerArchivoMovimiento(Integer movimientoId) {
        Movimiento movimiento= buscarMovimientoPorID(movimientoId);

        if(movimiento.getRutaArchivo()==null){
            throw new ValidacionException("El movimiento no contiene un archivo asociado");

        }
        Path ruta= Paths.get(movimiento.getRutaArchivo());

        try {

            Resource resource =
                    new UrlResource(ruta.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            }

        } catch (MalformedURLException e) {

            throw new ValidacionException(
                    "Error al cargar archivo");
        }

        throw new RecursoNoEncontradoException(
                "Archivo no encontrado");


    }

    private Integer validarCantidad(Integer cantidad) {
        if(cantidad==null|| cantidad<=0){
            throw new ValidacionException("El campo no puede ir vacio y debe ser mayor que 0");
        }
        return cantidad;
    }

    private void validarId(Integer id){
        if(id==null||id<=0){
            throw new ValidacionException("El campo id no puede ser vacio y debe ser mayor a 0 ");
        }
    }


}
