package com.MV_Equipos.Inventario.serviceImplementation;

import com.MV_Equipos.Inventario.entity.Producto;
import com.MV_Equipos.Inventario.repository.ProductRepository;
import com.MV_Equipos.Inventario.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImp implements ProductoService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Producto guardarProducto(Producto producto) {
        return productRepository.save(producto);
    }

    @Override
    public Optional<Producto> buscarPorID(Integer id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Producto> buscarProductos() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Producto> buscarPorClaveGeneral(String claveGeneral) {
        return productRepository.findByClaveGeneral(claveGeneral);
    }

    @Override
    public Producto editarParcial(Integer id, Producto productoActualizado) {
        Producto producto = productRepository.findById(id).orElse(null);//si el producto no existe el objeto es null si existe se guarda el objeto en producto
        if (producto == null) {
            return null;//si el objeto es null lo regresas de inmediato
        }
        if (productoActualizado.getNombre() != null) {
            producto.setNombre(productoActualizado.getNombre());
        }

        if (productoActualizado.getClaveGeneral() != null) {
            producto.setClaveGeneral(productoActualizado.getClaveGeneral());
        }
        if (productoActualizado.getTamano() != null) {
            producto.setTamano(productoActualizado.getTamano());
        }
        if (productoActualizado.getTipo() != null) {
            producto.setTipo(productoActualizado.getTipo());
        }
        if (productoActualizado.getNomenclatura() != null) {
            producto.setNomenclatura(productoActualizado.getNomenclatura());
        }

        if (productoActualizado.getStock() != null) {
            producto.setStock(productoActualizado.getStock());
        }
        if (productoActualizado.getBodega() != null) {
            producto.setBodega(productoActualizado.getBodega());
        }

        if (productoActualizado.getNotas() != null) {
            producto.setNotas(productoActualizado.getNotas());
        }
        return productRepository.save(producto);
    }

    @Override
    public List<Producto> buscarPorCoincidencia(String text) {


        return productRepository.findByNombreContainingIgnoreCaseOrClaveGeneralContainingIgnoreCaseOrBodegaContainingIgnoreCaseOrTipoContainingIgnoreCase(text, text, text, text);
    }
}
