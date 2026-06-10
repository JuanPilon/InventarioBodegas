package com.MV_Equipos.Inventario.Dto;


import com.MV_Equipos.Inventario.enums.TipoMovimiento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoResponseDto {
    private Integer idMovimiento;

    private Integer productoId;

    private Integer userId;

    private TipoMovimiento tipoMovimiento;

    private String usuario;

    private String producto;


    private Integer cantidad;

    private Integer stockFinal;


    private String nombreArchivo;

    private String rutaArchivo;

    private String tipoArchivo;

    private String comentarios;
}
