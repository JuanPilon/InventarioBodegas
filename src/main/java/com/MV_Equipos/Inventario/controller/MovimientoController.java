package com.MV_Equipos.Inventario.controller;

import com.MV_Equipos.Inventario.Dto.MovimientoMapper;
import com.MV_Equipos.Inventario.Dto.MovimientoRequestDto;
import com.MV_Equipos.Inventario.Dto.MovimientoResponseDto;
import com.MV_Equipos.Inventario.entity.Movimiento;
import com.MV_Equipos.Inventario.enums.TipoMovimiento;
import com.MV_Equipos.Inventario.service.MovimientoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping(path = "/Movimiento")
@CrossOrigin
public class MovimientoController {


    @Autowired
    private MovimientoService movimientoService;
    @Autowired
    private MovimientoMapper movimientoMapper;

    @GetMapping(path = "/todosLosMovimientos")
    public List<MovimientoResponseDto> todosLosMovimientos(){
        List<Movimiento>movimientosEncontrados=movimientoService.obtenerMovimientos();
        return movimientosEncontrados.stream()
                .map(movimientoMapper::toResponseDto)
                .toList();
    }

    @GetMapping(path="/movimientoPorId/{id}")
    public MovimientoResponseDto movimientoPorID(@PathVariable Integer id ){


        return movimientoMapper.toResponseDto(movimientoService.buscarMovimientoPorID(id));
    }

    @PostMapping(path = "/entrada")
    public MovimientoResponseDto registrarEntrada(@RequestParam Integer productoId,
            @RequestParam Integer userId,
            @RequestParam Integer cantidad,
            @RequestParam String comentarios,
            @RequestParam (required = false)
            MultipartFile archivo) {

        return movimientoMapper.toResponseDto(movimientoService.registrarEntrada(
                productoId,
                userId,
                cantidad,
                comentarios,
                archivo
        ));
    }
    @PostMapping(path = "/salida")
    public MovimientoResponseDto registrarSalida(@Valid @RequestBody MovimientoRequestDto movimiento){
        Movimiento movimientoAcrear= movimientoMapper.toEntity(movimiento);
        return movimientoMapper.toResponseDto(movimientoService.registrarSalida(movimiento.getProductoId().getId(),movimiento.getUserId().getId(),movimiento.getCantidad(),movimiento.getComentarios())) ;
    }

    @GetMapping(path = "/obtenerPorID/{id}")
    public List<MovimientoResponseDto>obtenerPorID(@PathVariable Integer id){
        List<Movimiento> movimientosEncontrados=movimientoService.obtenerPorID(id);

        return movimientosEncontrados.stream()
                .map(movimientoMapper::toResponseDto)
                .toList();
    }
    @GetMapping(path ="/obtenerTipoDeMovimiento/{movimiento}")
    public List<MovimientoResponseDto>obtenerEntradasYSalidas(@PathVariable TipoMovimiento movimiento){
        List<Movimiento> movimientosEncontrados=movimientoService.obtenerEntradas(movimiento);

        return movimientosEncontrados.stream()
                .map(movimientoMapper::toResponseDto)
                .toList();

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
