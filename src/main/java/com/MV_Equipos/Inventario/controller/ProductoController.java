package com.MV_Equipos.Inventario.controller;

import com.MV_Equipos.Inventario.Dto.ProductEditRequestDto;
import com.MV_Equipos.Inventario.Dto.ProductoMapper;
import com.MV_Equipos.Inventario.Dto.ProductoRequestDto;
import com.MV_Equipos.Inventario.Dto.ProductoResponseDto;
import com.MV_Equipos.Inventario.entity.Producto;
import com.MV_Equipos.Inventario.service.ProductoService;
import com.MV_Equipos.Inventario.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/producto")
@CrossOrigin
@Tag(
        name = "Productos",
        description = "Gestión del catálogo de productos"
)
public class ProductoController {
    @Autowired
    private ProductoService productoService;
    @Autowired
    private ProductoMapper productoMapper;

    @GetMapping(path = "/buscar")
    public ResponseEntity <List<ProductoResponseDto>> buscarTodos() {

        List<Producto> productosEncontrados = productoService.buscarProductos();


        return ResponseEntity.ok( productosEncontrados.stream()
                .map(productoMapper::toResponseDto)
                .toList());
    }

    @Operation(
            summary = "Registrar producto",
            description = "Crea un nuevo producto dentro del inventario"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Producto creado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping(path = "/crear")
    public ResponseEntity <ProductoResponseDto> guardarProducto(@Valid @RequestBody ProductoRequestDto producto) {
        Producto productoArmado = productoMapper.toEntityDto(producto);


        return ResponseEntity.status(HttpStatus.CREATED).body(productoMapper.toResponseDto(productoService.guardarProducto(productoArmado)));
    }

    @GetMapping(path = "/buscarPorID/{id}")
    public ResponseEntity <ProductoResponseDto> buscarPorID(@PathVariable Integer id) {


        return ResponseEntity.ok(productoMapper.toResponseDto(productoService.buscarPorID(id)));
    }

    @GetMapping(path = "/buscarPorCode/{clave}")
    public ResponseEntity <ProductoResponseDto> buscarPorCode(@PathVariable String clave) {

        return ResponseEntity.ok(productoMapper.toResponseDto(productoService.buscarPorClaveGeneral(clave)));
    }

    @Operation(
            summary = "Editar producto",
            description = "Actualiza parcialmente la información del producto"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto actualizado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PatchMapping("/editar/{id}")
    public ResponseEntity <ProductoResponseDto> editarParcial(
            @PathVariable Integer id,
            @Valid @RequestBody ProductEditRequestDto productoActualizado) {
        Producto productoDatos=productoMapper.toEntityDtoEdit(productoActualizado);

        return ResponseEntity.ok(productoMapper.toResponseDto(productoService.editarParcial(id, productoDatos)));
    }

    @GetMapping("/buscarCoincidencia/{texto}")
    public ResponseEntity <List<ProductoResponseDto>> buscarCoincidencia(
            @PathVariable String texto) {
        List<Producto> productosEncontrados = productoService.buscarPorCoincidencia(texto);

        return ResponseEntity.ok(productosEncontrados.stream()
                .map(productoMapper::toResponseDto)
                .toList());

    }


}
