package com.MV_Equipos.Inventario.serviceImplementation;

import com.MV_Equipos.Inventario.entity.Producto;
import com.MV_Equipos.Inventario.repository.ProductRepository;
import com.MV_Equipos.Inventario.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public Optional<Producto> buscarPorCodigo(String code) {
        return productRepository.findByCode(code);
    }

    @Override
    public Producto editarParcial(Integer id, Producto productoActualizado) {
        Producto producto=productRepository.findById(id).orElse(null);//si el producto no existe el objeto es null si existe se guarda el objeto en producto
        if(producto==null){
            return null;//si el objeto es null lo regresas de inmediato
        }
        if(productoActualizado.getName()!=null){
            producto.setName(productoActualizado.getName());
        }
        if(productoActualizado.getCode()!=null){
            producto.setCode(productoActualizado.getCode());
        }

        if(productoActualizado.getSku()!=null){
            producto.setSku(productoActualizado.getSku());
        }
        if(productoActualizado.getPrice()!=null){
            producto.setPrice(productoActualizado.getPrice());
        }
        if(productoActualizado.getStock()!=null){
            producto.setStock(productoActualizado.getStock());
        }
        if (productoActualizado.getLocation()!=null){
            producto.setLocation(productoActualizado.getLocation());
        }

        if(productoActualizado.getNotes()!=null){
            producto.setNotes(productoActualizado.getNotes());
        }
        return productRepository.save(producto);
    }

    @Override
    public List<Producto> buscarPorCoincidencia(String text) {

        BigDecimal precio = null;//declaramos el precio null para poder si o no darle valor

        try{
            precio = new BigDecimal(text);//intentara realizar la conversion para revisar disponibilidar
        }catch (NumberFormatException e){
            // si no es número, simplemente ignoramos el precio
        }

        return productRepository
                .findByNameContainingIgnoreCaseOrCodeContainingIgnoreCaseOrSkuContainingIgnoreCaseOrPriceOrLocationContainingIgnoreCase(
                        text,
                        text,
                        text,
                        precio,
                        text
                );
    }
}
