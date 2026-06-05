package com.MV_Equipos.Inventario.serviceImplementation;

import com.MV_Equipos.Inventario.entity.Producto;
import com.MV_Equipos.Inventario.repository.ProductRepository;
import com.MV_Equipos.Inventario.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Service
public class ProductoServiceImp implements ProductoService {
    @Autowired
    private ProductRepository productRepository;
    @Transactional
    @Override
    public Producto guardarProducto(Producto producto) {
        producto.setNombre(normalizarTexto(producto.getNombre()));
        producto.setClaveGeneral(normalizarTexto(producto.getClaveGeneral()));
        producto.setTamano(normalizarTexto(producto.getTamano()));
        producto.setTipo(normalizarTexto(producto.getTipo()));
        producto.setBodega(normalizarTexto(producto.getBodega()));
        producto.setNomenclatura(normalizarTexto(producto.getNomenclatura()));
        return productRepository.save(producto);
    }
    @Transactional(readOnly = true)
    @Override
    public Producto buscarPorID(Integer id) {//Se elimina la opcion de que no regrese nada por que nos estamos asegurando que se interrumpa con el runtime exception

        validarId(id);
        return productRepository.findById(id).orElseThrow(() ->new RuntimeException("Producto no encontrado"));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Producto> buscarProductos() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Producto buscarPorClaveGeneral(String claveGeneral) {
        validarTexto(claveGeneral);
        Producto producto= productRepository.findByClaveGeneral(normalizarTexto(claveGeneral));
        if(producto==null){
            throw new RuntimeException("No se encontro el producto por la clave registrada");
        }
        return producto;
    }

    @Transactional
    @Override
    public Producto editarParcial(Integer id, Producto productoActualizado) {
        Producto producto = buscarPorID(id);
        if (productoActualizado.getNombre() != null) {
            producto.setNombre(normalizarTexto(productoActualizado.getNombre()));
        }

        if (productoActualizado.getClaveGeneral() != null) {
            producto.setClaveGeneral(normalizarTexto(productoActualizado.getClaveGeneral()));
        }
        if(productoActualizado.getPrecioPorUnidad() != null){

            if(productoActualizado.getPrecioPorUnidad()
                    .compareTo(BigDecimal.ZERO) <= 0){

                throw new RuntimeException(
                        "El precio debe ser mayor a cero");
            }

            producto.setPrecioPorUnidad(productoActualizado.getPrecioPorUnidad());
        }
        if (productoActualizado.getFechaDePrecio() != null) {
            if(productoActualizado.getFechaDePrecio().isAfter(LocalDate.now())){
                throw new RuntimeException("La fecha que quieres cambiar debe ser actual o anterior");
            }
            producto.setFechaDePrecio(productoActualizado.getFechaDePrecio());
        }

        if (productoActualizado.getTamano() != null) {
            producto.setTamano(normalizarTexto(productoActualizado.getTamano()));
        }
        if (productoActualizado.getTipo() != null) {
            producto.setTipo(normalizarTexto(productoActualizado.getTipo()));
        }
        if (productoActualizado.getNomenclatura() != null) {
            producto.setNomenclatura(normalizarTexto(productoActualizado.getNomenclatura()));
        }

        if(productoActualizado.getStock() != null){

            if(productoActualizado.getStock() < 0){
                throw new RuntimeException(
                        "El stock no puede ser negativo");
            }

            producto.setStock(productoActualizado.getStock());
        }
        if (productoActualizado.getBodega() != null) {
            producto.setBodega(normalizarTexto(productoActualizado.getBodega()));
        }

        if (productoActualizado.getNotas() != null) {
            producto.setNotas(productoActualizado.getNotas());
        }
        return productRepository.save(producto);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Producto> buscarPorCoincidencia(String text) {
        validarTexto(text);
        String texto=normalizarTexto(text);
        List<Producto> productosEncontrados=productRepository.findByNombreContainingIgnoreCaseOrClaveGeneralContainingIgnoreCaseOrBodegaContainingIgnoreCaseOrTipoContainingIgnoreCase(texto, texto, texto, texto);
        if(productosEncontrados.isEmpty()){
            throw new RuntimeException("No se encontraron coincidencias con los datos ingresados");

        }
        return productosEncontrados;
}

    //Funciones para validaciones en service

    private String normalizarTexto(String texto) {

        return texto.trim().toUpperCase();
    }
    private void validarId(Integer id){

        if(id == null || id <= 0){
            throw new RuntimeException(
                    "Los id deben ser mayores a 0");
        }
    }
    private void validarTexto(String texto){
        if(texto == null || texto.isBlank()){
            throw new RuntimeException(
                    "No puedes mandar campos vacíos");
        }
    }

}
