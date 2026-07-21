package com.MV_Equipos.Inventario.serviceImplementation;

import com.MV_Equipos.Inventario.Exception.RecursoNoEncontradoException;
import com.MV_Equipos.Inventario.Exception.ValidacionException;
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
        producto.setDescripcionDelProducto(normalizarTexto(producto.getDescripcionDelProducto()));
        producto.setClaveGeneral(normalizarTexto(producto.getClaveGeneral()));
        producto.setMedida(normalizarTexto(producto.getMedida()));
        producto.setEmbalaje(normalizarTexto(producto.getEmbalaje()));
        producto.setMedida(normalizarTexto(producto.getMedida()));
        producto.setCategoria(normalizarTexto(producto.getCategoria()));
        producto.setBodegas(normalizarTexto(producto.getBodegas()));
        producto.setUnidadMinima(normalizarTexto(producto.getUnidadMinima()));
        return productRepository.save(producto);
    }
    @Transactional(readOnly = true)
    @Override
    public Producto buscarPorID(Integer id) {//Se elimina la opcion de que no regrese nada por que nos estamos asegurando que se interrumpa con el runtime exception

        validarId(id);
        return productRepository.findById(id).orElseThrow(() ->new RecursoNoEncontradoException("Producto no encontrado"));
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
            throw new RecursoNoEncontradoException("No se encontro el producto por la clave registrada");
        }
        return producto;
    }

    @Transactional
    @Override
    public Producto editarParcial(Integer id, Producto productoActualizado) {
        Producto producto = buscarPorID(id);
        if (productoActualizado.getDescripcionDelProducto() != null) {
            validarTexto(productoActualizado.getDescripcionDelProducto());
            producto.setDescripcionDelProducto(normalizarTexto(productoActualizado.getDescripcionDelProducto()));
        }

        if (productoActualizado.getClaveGeneral() != null) {
            validarTexto(productoActualizado.getClaveGeneral());
            producto.setClaveGeneral(normalizarTexto(productoActualizado.getClaveGeneral()));
        }
        if(productoActualizado.getPrecioPorUnidadCompra() != null){

            if(productoActualizado.getPrecioPorUnidadCompra()
                    .compareTo(BigDecimal.ZERO) <= 0){

                throw new ValidacionException(
                        "El precio debe ser mayor a cero");
            }

            producto.setPrecioPorUnidadCompra(productoActualizado.getPrecioPorUnidadCompra());
        }
        if (productoActualizado.getFechaDeCompra() != null) {
            if(productoActualizado.getFechaDeCompra().isAfter(LocalDate.now())){
                throw new ValidacionException("La fecha que quieres cambiar debe ser actual o anterior");
            }
            producto.setFechaDeCompra(productoActualizado.getFechaDeCompra());
        }

        if (productoActualizado.getMedida() != null) {
            validarTexto(productoActualizado.getMedida());
            producto.setMedida(normalizarTexto(productoActualizado.getMedida()));
        }
        if (productoActualizado.getMarca() != null) {
            validarTexto(productoActualizado.getMarca());
            producto.setMarca(normalizarTexto(productoActualizado.getMarca()));
        }
        if(productoActualizado.getEmbalaje() != null) {
            validarTexto(productoActualizado.getEmbalaje());
            producto.setEmbalaje(normalizarTexto(productoActualizado.getEmbalaje()));
        }
        if (productoActualizado.getCategoria() != null) {
            validarTexto(productoActualizado.getCategoria());
            producto.setCategoria(normalizarTexto(productoActualizado.getCategoria()));
        }
        if (productoActualizado.getUnidadMinima() != null) {
            validarTexto(productoActualizado.getUnidadMinima());
            producto.setUnidadMinima(normalizarTexto(productoActualizado.getUnidadMinima()));
        }

        if(productoActualizado.getStock() != null){

            if(productoActualizado.getStock() < 0){
                throw new ValidacionException(
                        "El stock no puede ser negativo");
            }

            producto.setStock(productoActualizado.getStock());
        }
        if (productoActualizado.getBodegas() != null) {
            validarTexto(productoActualizado.getBodegas());
            producto.setBodegas(normalizarTexto(productoActualizado.getBodegas()));
        }

        if (productoActualizado.getNotasGenerales() != null) {
            validarTexto(productoActualizado.getNotasGenerales());
            producto.setNotasGenerales(productoActualizado.getNotasGenerales());
        }
        return productRepository.save(producto);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Producto> buscarPorCoincidencia(String text) {
        validarTexto(text);
        String texto=normalizarTexto(text);
        List<Producto> productosEncontrados=productRepository.findByDescripcionDelProductoContainingIgnoreCaseOrClaveGeneralContainingIgnoreCaseOrBodegasContainingIgnoreCaseOrCategoriaContainingIgnoreCase(texto, texto, texto, texto);
        if(productosEncontrados.isEmpty()){
            throw new RecursoNoEncontradoException("No se encontraron coincidencias con los datos ingresados");

        }
        return productosEncontrados;
}

    //Funciones para validaciones en service

    private String normalizarTexto(String texto) {

        return texto.trim().toUpperCase();
    }
    private void validarId(Integer id){

        if(id == null || id <= 0){
            throw new ValidacionException(
                    "Los id deben ser mayores a 0");
        }
    }
    private void validarTexto(String texto){
        if(texto == null || texto.isBlank()){
            throw new ValidacionException(
                    "No puedes mandar campos vacíos");
        }
    }

}
