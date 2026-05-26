package com.MV_Equipos.Inventario.controller;

import com.MV_Equipos.Inventario.entity.Movimiento;
import com.MV_Equipos.Inventario.enums.TipoMovimiento;
import com.MV_Equipos.Inventario.service.MovimientoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/Movimiento")
@CrossOrigin
public class MovimientoController {


    @Autowired
    private MovimientoService movimientoService;

    @GetMapping(path = "/todosLosMovimientos")
    public List<Movimiento> todosLosMovimientos(){return movimientoService.obtenerMovimientos();}

    @PostMapping(path = "/entrada")
    public Movimiento registrarEntrada(@Valid @RequestBody Movimiento movimiento){
        return movimientoService.registrarEntrada(movimiento.getProductoId().getId(),movimiento.getUserId().getId(),movimiento.getCantidad(),movimiento.getComentarios());
    }
    @PostMapping(path = "/salida")
    public Movimiento registrarSalida(@Valid @RequestBody Movimiento movimiento){
        return movimientoService.registrarSalida(movimiento.getProductoId().getId(),movimiento.getUserId().getId(),movimiento.getCantidad(),movimiento.getComentarios());
    }

    @GetMapping(path = "/obtenerPorID/{id}")
    public List<Movimiento>obtenerPorID(@PathVariable Integer id){
        return movimientoService.obtenerPorID(id);
    }
    @GetMapping(path ="/obtenerEntradas/{movimiento}")
    public List<Movimiento>obtenerEntradas(@PathVariable TipoMovimiento movimiento){
        return movimientoService.obtenerEntradas(movimiento);
    }


}
