package com.MV_Equipos.Inventario.service;

import com.MV_Equipos.Inventario.entity.Movimiento;

import java.util.List;

public interface MovimientoService {

    Movimiento registrarEntrada(Integer productoId,
                                Integer usuarioId,
                                Integer cantidad,
                                String comentarios);

    Movimiento registrarSalida(Integer productoId,
                               Integer usuarioId,
                               Integer cantidad,
                               String comentarios);

    List<Movimiento> obtenerMovimientos();
}
