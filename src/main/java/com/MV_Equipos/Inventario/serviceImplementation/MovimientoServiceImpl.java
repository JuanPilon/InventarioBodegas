package com.MV_Equipos.Inventario.serviceImplementation;

import com.MV_Equipos.Inventario.entity.Movimiento;
import com.MV_Equipos.Inventario.entity.Producto;
import com.MV_Equipos.Inventario.entity.Usuario;
import com.MV_Equipos.Inventario.enums.TipoMovimiento;
import com.MV_Equipos.Inventario.repository.MovementRepository;
import com.MV_Equipos.Inventario.repository.ProductRepository;
import com.MV_Equipos.Inventario.repository.UserRepository;
import com.MV_Equipos.Inventario.service.MovimientoService;
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
import java.util.Optional;

@Service
public class MovimientoServiceImpl implements MovimientoService {

    @Autowired
    private MovementRepository movementRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;



    @Transactional
    @Override
    public Movimiento registrarEntrada(Integer productoId, Integer usuarioId, Integer cantidad, String comentarios, MultipartFile archivo) {
        Optional<Producto> productoEncontrado = productRepository.findById(productoId);
        Optional<Usuario> usuarioEncontrado = userRepository.findById(usuarioId);
        String tipoArchivo = null;
        String nombreArchivo = null;
        Path rutaArchivo = null;
        String rutaCarpeta ="uploads/entradas/";



        if (archivo != null && !archivo.isEmpty()) {

            tipoArchivo = archivo.getContentType();



            if (tipoArchivo == null ||(!tipoArchivo.equals("application/pdf") &&
                            !tipoArchivo.equals("application/xml") &&
                            !tipoArchivo.equals("text/xml"))) {

                throw new RuntimeException(
                        "Solo se permiten archivos PDF o XML");
            }
            nombreArchivo = archivo.getOriginalFilename();
            rutaArchivo =Paths.get(rutaCarpeta + nombreArchivo);
        }



        if (productoEncontrado.isPresent() && usuarioEncontrado.isPresent()) {
            Integer stockAnterior = productoEncontrado.get().getStock();
            Integer stockFinal = stockAnterior + cantidad;
            productoEncontrado.get().setStock(stockFinal);
            productRepository.save(productoEncontrado.get());
            Movimiento movimiento = Movimiento.builder()//gracias a loombok podemos crear los objetos de forma mas estructurada
                    .productoId(productoEncontrado.get())
                    .userId(usuarioEncontrado.get())
                    .tipoMovimiento(TipoMovimiento.ENTRADA)
                    .cantidad(cantidad)
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

                    throw new RuntimeException(
                            "Error al guardar el archivo");
                }
            }


            return movementRepository.save(movimiento);
        }
        throw new RuntimeException("Producto o usuario no encontrado");

    }

    @Transactional
    @Override
    public Movimiento registrarSalida(Integer productoId, Integer usuarioId, Integer cantidad, String comentarios) {
        Optional<Producto> productoEncontrado = productRepository.findById(productoId);
        Optional<Usuario> usuarioEncontrado = userRepository.findById(usuarioId);

        if (productoEncontrado.isPresent() && usuarioEncontrado.isPresent()) {
            Integer stockAnterior = productoEncontrado.get().getStock();
            Integer stockFinal = stockAnterior - cantidad;
            if (stockFinal >= 0) {
                productoEncontrado.get().setStock(stockFinal);
                productRepository.save(productoEncontrado.get());
                Movimiento movimiento = Movimiento.builder()
                        .productoId(productoEncontrado.get())
                        .userId(usuarioEncontrado.get())
                        .tipoMovimiento(TipoMovimiento.SALIDA)
                        .cantidad(cantidad)
                        .stockFinal(stockFinal)
                        .comentarios(comentarios)
                        .build();
                return movementRepository.save(movimiento);
            }

            throw new RuntimeException("No puedes darle salida a stock que no tienes");
        }
        throw new RuntimeException("Producto o usuario no encontrado");
    }

    @Transactional(readOnly = true)
    @Override
    public List<Movimiento> obtenerMovimientos() {
        return movementRepository.findAll();
    }
    @Transactional(readOnly = true)
    @Override
    public List<Movimiento> obtenerPorID(Integer productoId) {
        return movementRepository.findByProductoId_Id(productoId);
    }

    @Override
    public List<Movimiento> obtenerEntradas(TipoMovimiento movimientoEnv) {
        TipoMovimiento movimiento = movimientoEnv;
        return movementRepository.findByTipoMovimiento(movimiento);
    }

    @Override
    public Optional<Movimiento> buscarMovimientoPorID(Integer id) {
        return movementRepository.findById(id);
    }

    @Override
    public Resource obtenerArchivoMovimiento(Integer movimientoId) {
        Movimiento movimiento= movementRepository.findById(movimientoId).orElse(null);
        if(movimiento==null){
            throw new RuntimeException("El movimiento no existe revisar el dato");
        }
        Path ruta= Paths.get(movimiento.getRutaArchivo());
        if(ruta==null){
            throw new RuntimeException("El movimiento no contiene un archivo asociado");

        }
        try {

            Resource resource =
                    new UrlResource(ruta.toUri());

            if (resource.exists()) {
                return resource;
            }

        } catch (MalformedURLException e) {

            throw new RuntimeException(
                    "Error al cargar archivo");
        }

        throw new RuntimeException(
                "Archivo no encontrado");


    }


}
