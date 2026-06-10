package com.MV_Equipos.Inventario.controller;

import com.MV_Equipos.Inventario.Dto.MovimientoMapper;
import com.MV_Equipos.Inventario.Dto.MovimientoRequestDto;
import com.MV_Equipos.Inventario.Dto.MovimientoResponseDto;
import com.MV_Equipos.Inventario.entity.Movimiento;
import com.MV_Equipos.Inventario.enums.TipoMovimiento;
import com.MV_Equipos.Inventario.service.MovimientoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping(path = "/Movimiento")
@CrossOrigin
@Tag(
        name = "Movimientos",
        description = "Entradas, salidas y consultas de movimientos de inventario"
)
public class MovimientoController {


    @Autowired
    private MovimientoService movimientoService;
    @Autowired
    private MovimientoMapper movimientoMapper;

    @GetMapping(path = "/todosLosMovimientos")
    public ResponseEntity <List<MovimientoResponseDto>> todosLosMovimientos(){
        List<Movimiento>movimientosEncontrados=movimientoService.obtenerMovimientos();
        return ResponseEntity.ok(movimientosEncontrados.stream()
                .map(movimientoMapper::toResponseDto)
                .toList());
    }

    @GetMapping(path="/movimientoPorId/{id}")
    public  ResponseEntity <MovimientoResponseDto> movimientoPorID(@PathVariable Integer id ){


        return ResponseEntity.ok(movimientoMapper.toResponseDto(movimientoService.buscarMovimientoPorID(id)));
    }

    @Operation(
            summary = "Registrar entrada",
            description = "Registra una entrada de inventario y actualiza el stock"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Entrada registrada"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping(path = "/entrada")
    public ResponseEntity <MovimientoResponseDto> registrarEntrada(@RequestParam Integer productoId,
            @RequestParam Integer userId,
            @RequestParam Integer cantidad,
            @RequestParam String comentarios,
            @RequestParam (required = false)
            MultipartFile archivo) {

        return ResponseEntity.status(HttpStatus.CREATED).body(movimientoMapper.toResponseDto(movimientoService.registrarEntrada(
                productoId,
                userId,
                cantidad,
                comentarios,
                archivo
        )));
    }

    @Operation(
            summary = "Registrar salida",
            description = "Registra una salida de inventario y descuenta stock"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Salida registrada"),
            @ApiResponse(responseCode = "400", description = "Stock insuficiente o datos inválidos")
    })
    @PostMapping(path = "/salida")
    public ResponseEntity <MovimientoResponseDto> registrarSalida(@Valid @RequestBody MovimientoRequestDto movimiento){

        return ResponseEntity.status(HttpStatus.CREATED).body(movimientoMapper.toResponseDto(movimientoService.registrarSalida(movimiento.getProductoId(),movimiento.getUserId(),movimiento.getCantidad(),movimiento.getComentarios()))) ;
    }

    @GetMapping(path = "/obtenerMovimientosPorIDProducto/{id}")
    public ResponseEntity <List<MovimientoResponseDto>>obtenerPorID(@PathVariable Integer id){
        List<Movimiento> movimientosEncontrados=movimientoService.obtenerPorID(id);

        return ResponseEntity.ok(movimientosEncontrados.stream()
                .map(movimientoMapper::toResponseDto)
                .toList());
    }
    @GetMapping(path ="/obtenerTipoDeMovimiento/{movimiento}")
    public ResponseEntity <List<MovimientoResponseDto>> obtenerEntradasYSalidas(@PathVariable TipoMovimiento movimiento){
        List<Movimiento> movimientosEncontrados=movimientoService.obtenerEntradas(movimiento);

        return ResponseEntity.ok(movimientosEncontrados.stream()
                .map(movimientoMapper::toResponseDto)
                .toList());

    }
    @GetMapping("/{id}/archivo")
    public ResponseEntity<Resource> descargarArchivo(
            @PathVariable Integer id) {

        Movimiento movimiento =
                movimientoService.buscarMovimientoPorID(id);

        Resource archivo =
                movimientoService.obtenerArchivoMovimiento(id);

        return ResponseEntity.ok()
                .contentType(
                        MediaType.parseMediaType(
                                movimiento.getTipoArchivo()))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" +
                                movimiento.getNombreArchivo() +
                                "\"")
                .body(archivo);
    }



}
