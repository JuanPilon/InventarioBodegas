package com.MV_Equipos.Inventario.Dto;

import com.MV_Equipos.Inventario.enums.TipoRol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    private String name;
    private String username;
    private String password;
    private TipoRol rol;
}
