package com.MV_Equipos.Inventario.service;

import com.MV_Equipos.Inventario.entity.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    Producto guardarProducto(Producto producto);

    Producto buscarPorID(Integer id);

    List<Producto> buscarProductos();

    Producto buscarPorClaveGeneral(String claveGeneral);

    Producto editarParcial(Integer id, Producto productoActualizado);

    List<Producto> buscarPorCoincidencia(String text);

}
