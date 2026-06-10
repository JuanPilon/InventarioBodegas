package com.MV_Equipos.Inventario.Dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
public class ProductoRequestDto {



    private String nombre;


    private String claveGeneral;


    private String tamano;


    private String tipo;


    private String nomenclatura;


    private BigDecimal precioPorUnidad;


    private LocalDate fechaDePrecio;


    private Integer stock;


    private String bodega;


    private String notas;


}
