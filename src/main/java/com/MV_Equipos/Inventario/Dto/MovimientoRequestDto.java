package com.MV_Equipos.Inventario.Dto;

import com.MV_Equipos.Inventario.entity.Producto;
import com.MV_Equipos.Inventario.entity.Usuario;
import com.MV_Equipos.Inventario.enums.TipoMovimiento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovimientoRequestDto {


    private Producto productoId;


    private Usuario userId;


    private TipoMovimiento tipoMovimiento;


    private Integer cantidad;


    private Integer stockFinal;


    private String nombreArchivo;


    private String rutaArchivo;


    private String tipoArchivo;

    private String comentarios;


}
