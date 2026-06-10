package com.MV_Equipos.Inventario.Dto;

import com.MV_Equipos.Inventario.entity.Producto;
import com.MV_Equipos.Inventario.entity.Usuario;
import com.MV_Equipos.Inventario.enums.TipoMovimiento;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovimientoRequestDto {


    @NotNull(message = "Debe seleccionar un producto")
    private Integer productoId;


    @NotNull(message = "Debe seleccionar un usuario")
    private Integer userId;


    @NotNull(message = "Debe indicar el tipo de movimiento")
    private TipoMovimiento tipoMovimiento;


    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser mayor a 0")
    private Integer cantidad;




    private String comentarios;


}
