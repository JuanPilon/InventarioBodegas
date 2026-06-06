package com.MV_Equipos.Inventario.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Locale;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private LocalDateTime fecha;
    private Integer codigo;
    private String error;
}
