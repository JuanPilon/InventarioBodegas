package com.MV_Equipos.Inventario.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductoResponseDto {
    private Integer id;

    private String descripcionDelProducto;


    private String claveGeneral;


    private String medida;

    private String embalaje;


    private String categoria;

    private String marca;

    private String unidadMinima;


    private BigDecimal precioPorUnidadCompra;


    private LocalDate fechaDeCompra;


    private Integer stock;


    private String bodegas;


    private String notasGenerales;

    private Timestamp cDate;


}
