package com.MV_Equipos.Inventario.Dto;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import java.time.LocalDate;

@Schema(description = "Datos necesarios para crear un producto")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductoRequestDto {


    @Schema(
            description = "Nombre del producto",
            example = "Guantes"
    )
    @NotBlank(message = "El camo nombre de prouducto no debe quedar vacio")
    @Size(max = 255)
    private String descripcionDelProducto;


    @NotBlank(message = "El campo clave de prouducto no debe quedar vacio")
    @Size(max = 120)
    private String claveGeneral;


    @NotBlank(message = "El campo tamaño no debe quedar vacio")
    @Size(max = 45)
    private String medida;

    @NotBlank(message = "El campo embalaje no debe quedar vacio")
    @Size(max = 100)
    private String embalaje;

    @NotBlank(message = "El campo marca no debe quedar vacio")
    @Size(max = 100)
    private String marca;


    @NotBlank(message = "El campo Tipo no debe quedar vacio")
    @Size(max = 45)
    private String categoria;

    @NotBlank(message = "El campo momenclatura no debe quedar vacio")
    @Size(max = 45)
    private String unidadMinima;

    @NotNull(message = "Precio obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    private BigDecimal precioPorUnidadCompra;


    @NotNull(message = "Por favor registra la fecha del cambio de precio")
    @PastOrPresent(message = "La fecha no puede ser futura")
//Indica que solo se puede registrar una fecha presente o pasada pero nunca futura
    private LocalDate fechaDeCompra;

    @NotNull(message = "Stock obligatorio")
    @PositiveOrZero(message = "El stock no puede ser negativo")
    private Integer stock;

    @NotBlank(message = "El campo Bodega no debe quedar vacio")
    @Size(max = 45)
    private String bodegas;


    private String notasGenerales;
}
