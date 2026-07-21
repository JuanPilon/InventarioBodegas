package com.MV_Equipos.Inventario.Dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ProductEditRequestDto {


    @Size(min =2,max = 255)
    private String descripcionDelProducto;



    @Size(min =2,max = 120)
    private String claveGeneral;



    @Size(min =2,max = 45)
    private String medida;

    @Size(min =2,max = 100)
    private String marca;

    @Size(min =2,max = 100)
    private String embalaje;


    @Size(min =2, max = 45)
    private String categoria;

    @Size(min =2, max = 45)
    private String unidadMinima;

    @DecimalMin(value = "0.0",inclusive = false,message = "El precio debe ser mayor a 0")
    private BigDecimal precioPorUnidadCompra;



    @PastOrPresent(message = "La fecha no puede ser futura")//Indica que solo se puede registrar una fecha presente o pasada pero nunca futura
    private LocalDate fechaDeCompra;

    @PositiveOrZero(message = "El stock no puede ser negativo")
    private Integer stock;

    @Size(max = 45)
    private String bodegas;


    private String notasGenerales;

}
