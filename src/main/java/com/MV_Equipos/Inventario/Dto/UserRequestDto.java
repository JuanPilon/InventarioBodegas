package com.MV_Equipos.Inventario.Dto;

import com.MV_Equipos.Inventario.enums.TipoRol;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Datos necesarios para crear un usuario")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    @Schema(
            description = "Nombre completo",
            example = "Juan Pilon"
    )
    @NotBlank(message = "No puedes dejar el campo nombre vacio")
    @Size(min = 3, max = 80)
    private String name;

    @Schema(
            description = "Nombre de usuario",
            example = "JPILON23"
    )
    @NotBlank(message = "No puedes dejar el campo username vacio")
    @Size(min = 4, max = 80,message = "Almenos 4 caracteres ")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username Invalido")
    private String username;

    @Schema(
            description = "Contraseña",
            example = "P12345678"
    )
    @NotBlank(message = "No puedes dejar el campo password vacio")
    @Size(min = 8, max = 255,message = "Almenos 8 caracteres ")
    private String password;

    @Schema(
            description = "Rol del usuario",
            example = "ADMIN"
    )
    private TipoRol rol;

}
