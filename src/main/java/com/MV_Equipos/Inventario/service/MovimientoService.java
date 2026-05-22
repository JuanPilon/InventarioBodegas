package com.MV_Equipos.Inventario.service;

import com.MV_Equipos.Inventario.entity.Movimiento;
import com.MV_Equipos.Inventario.enums.TipoMovimiento;

import java.util.List;
import java.util.Optional;

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

    List<Movimiento> obtenerPorID(Integer productoId);
    List<Movimiento>obtenerEntradas(TipoMovimiento movimiento);


}
