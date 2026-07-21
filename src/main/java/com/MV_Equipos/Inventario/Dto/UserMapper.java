package com.MV_Equipos.Inventario.Dto;

import com.MV_Equipos.Inventario.entity.Usuario;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public Usuario toEntity(UserRequestDto dto) {//vamos convertir los datos enviados a un objeto de tipo usuario

        return Usuario.builder()
                .name(dto.getName())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .rol(dto.getRol())
                .build();

    }

    public UserResponseDto toResponse(Usuario entity) {// vamos a convertir el objeto creado en un objeto de tipo respuesta
        return UserResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .username(entity.getUsername())
                .rol(String.valueOf(entity.getRol()))
                .build();
    }
}