package com.MV_Equipos.Inventario.controller;

import com.MV_Equipos.Inventario.Dto.ProductoMapper;
import com.MV_Equipos.Inventario.Dto.ProductoRequestDto;
import com.MV_Equipos.Inventario.Dto.ProductoResponseDto;
import com.MV_Equipos.Inventario.entity.Producto;
import com.MV_Equipos.Inventario.service.ProductoService;
import com.MV_Equipos.Inventario.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/producto")
@CrossOrigin
public class ProductoController {
    @Autowired
    private ProductoService productoService;
    @Autowired
    private ProductoMapper productoMapper;

    @GetMapping(path = "/buscar")
    public List<ProductoResponseDto> buscarTodos() {

        List<Producto> productosEncontrados = productoService.buscarProductos();


        return productosEncontrados.stream()
                .map(productoMapper::toResponseDto)
                .toList();
    }

    @PostMapping(path = "/crear")
    public ProductoResponseDto guardarProducto(@Valid @RequestBody ProductoRequestDto producto) {
        Producto productoArmado = productoMapper.toEntityDto(producto);


        return productoMapper.toResponseDto(productoService.guardarProducto(productoArmado));
    }

    @GetMapping(path = "/buscarPorID/{id}")
    public ProductoResponseDto buscarPorID(@PathVariable Integer id) {


        return productoMapper.toResponseDto(productoService.buscarPorID(id));
    }

    @GetMapping(path = "/buscarPorCode/{clave}")
    public ProductoResponseDto buscarPorCode(@PathVariable String clave) {

        return productoMapper.toResponseDto(productoService.buscarPorClaveGeneral(clave));
    }

    @PatchMapping("/editar/{id}")
    public ProductoResponseDto editarParcial(
            @PathVariable Integer id,
            @RequestBody Producto productoActualizado) {

        return productoMapper.toResponseDto(productoService.editarParcial(id, productoActualizado));
    }

    @GetMapping("/buscarCoincidencia/{texto}")
    public List<ProductoResponseDto> buscarCoincidencia(
            @PathVariable String texto) {
        List<Producto> productosEncontrados = productoService.buscarPorCoincidencia(texto);

        return productosEncontrados.stream()
                .map(productoMapper::toResponseDto)
                .toList();

    }


}
