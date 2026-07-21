package com.MV_Equipos.Inventario.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResponse {

    private String token;

    private String username;

    private String rol;
}
