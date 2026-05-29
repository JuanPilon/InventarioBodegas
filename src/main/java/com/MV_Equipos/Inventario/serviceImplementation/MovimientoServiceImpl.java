package com.MV_Equipos.Inventario.serviceImplementation;

import com.MV_Equipos.Inventario.entity.Movimiento;
import com.MV_Equipos.Inventario.entity.Producto;
import com.MV_Equipos.Inventario.entity.Usuario;
import com.MV_Equipos.Inventario.enums.TipoMovimiento;
import com.MV_Equipos.Inventario.repository.MovementRepository;
import com.MV_Equipos.Inventario.repository.ProductRepository;
import com.MV_Equipos.Inventario.repository.UserRepository;
import com.MV_Equipos.Inventario.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    @Override
    public Movimiento registrarEntrada(Integer productoId, Integer usuarioId, Integer cantidad, String comentarios, MultipartFile archivo) {
        Optional<Producto> productoEncontrado = productRepository.findById(productoId);
        Optional<Usuario> usuarioEncontrado = userRepository.findById(usuarioId);
        String tipoArchivo = archivo.getContentType();

        if (!tipoArchivo.equals("application/pdf") &&
                !tipoArchivo.equals("application/xml") &&
                !tipoArchivo.equals("text/xml")) {

            throw new RuntimeException("Solo se permiten archivos PDF o XML");
        }


        if (productoEncontrado.isPresent() && usuarioEncontrado.isPresent()) {
            Integer stockAnterior = productoEncontrado.get().getStock();
            Integer stockFinal = stockAnterior + cantidad;
            productoEncontrado.get().setStock(stockFinal);
            productRepository.save(productoEncontrado.get());
            Movimiento movimiento = Movimiento.builder()
                    .productoId(productoEncontrado.get())
                    .userId(usuarioEncontrado.get())
                    .tipoMovimiento(TipoMovimiento.ENTRADA)
                    .cantidad(cantidad)
                    .stockFinal(stockFinal)
                    .comentarios(comentarios)
                    .build();
            return movementRepository.save(movimiento);
        }
        throw new RuntimeException("Producto o usuario no encontrado");

    }

    @Override
    public Movimiento registrarSalida(Integer productoId, Integer usuarioId, Integer cantidad, String comentarios) {
        Optional<Producto> productoEncontrado = productRepository.findById(productoId);
        Optional<Usuario> usuarioEncontrado = userRepository.findById(usuarioId);

        if (productoEncontrado.isPresent() && usuarioEncontrado.isPresent()) {
            Integer stockAnterior = productoEncontrado.get().getStock();
            Integer stockFinal = stockAnterior - cantidad;
            if(stockFinal>=0){
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

    @Override
    public List<Movimiento> obtenerMovimientos() {
        return movementRepository.findAll();
    }

    @Override
    public List<Movimiento> obtenerPorID(Integer productoId) {
        return movementRepository.findByProductoId_Id(productoId);
    }

    @Override
    public List<Movimiento> obtenerEntradas(TipoMovimiento movimientoEnv) {
        TipoMovimiento movimiento=movimientoEnv;
        return movementRepository.findByTipoMovimiento(movimiento);
    }



}
