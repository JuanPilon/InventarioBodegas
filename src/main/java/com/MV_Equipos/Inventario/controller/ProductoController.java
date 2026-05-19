package com.MV_Equipos.Inventario.controller;

import com.MV_Equipos.Inventario.entity.Producto;
import com.MV_Equipos.Inventario.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/producto")
@CrossOrigin
public class ProductoController {
    @Autowired
    private ProductoService productoService;


    @GetMapping(path = "/buscar")
    public List<Producto> buscarTodos() {
        return productoService.buscarProductos();
    }

    @PostMapping(path = "/crear")
    public Producto guardarProducto(@RequestBody Producto producto) {
        return productoService.guardarProducto(producto);
    }

    @GetMapping(path = "/buscarPorID/{id}")
    public Optional<Producto> buscarPorID(@PathVariable Integer id) {
        return productoService.buscarPorID(id);
    }

    @GetMapping(path = "/buscarPorCode/{clave}")
    public Optional<Producto> buscarPorCode(@PathVariable String clave) {
        return productoService.buscarPorClaveGeneral(clave);
    }

    @PatchMapping("/editar/{id}")
    public Producto editarParcial(
            @PathVariable Integer id,
            @RequestBody Producto productoActualizado) {

        return productoService.editarParcial(id, productoActualizado);
    }

    @GetMapping("/buscarCoincidencia/{texto}")
    public List<Producto> buscarCoincidencia(
            @PathVariable String texto) {

        return productoService.buscarPorCoincidencia(texto);

    }


}
