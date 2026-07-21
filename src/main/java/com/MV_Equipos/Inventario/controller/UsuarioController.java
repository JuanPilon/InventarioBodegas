package com.MV_Equipos.Inventario.controller;

import com.MV_Equipos.Inventario.Dto.UserMapper;
import com.MV_Equipos.Inventario.Dto.UserRequestDto;
import com.MV_Equipos.Inventario.Dto.UserResponseDto;
import com.MV_Equipos.Inventario.entity.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.MV_Equipos.Inventario.service.UsuarioService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/usuario")
@CrossOrigin
@Tag(
        name = "Usuarios",
        description = "Operaciones relacionadas con la administración de usuarios"
)
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UserMapper userMapper;

    @GetMapping(path = "/consultar")
    public ResponseEntity<List<UserResponseDto>> mostrarUsuarios() {
        List<Usuario> listaUsuarios=usuarioService.obtenerUsuarios();
        List<UserResponseDto> listaFinal= new ArrayList<>();
        for(Usuario usuario:listaUsuarios){
            UserResponseDto nuevaEntrada=userMapper.toResponse(usuario);
            listaFinal.add(nuevaEntrada);
        }
        return ResponseEntity.ok(listaFinal);
    }

    @Operation(
            summary = "Buscar usuario por ID",
            description = "Obtiene un usuario específico"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })


    @GetMapping(path = "/buscar/{id}")
    public ResponseEntity<UserResponseDto> obtenerPorID(@PathVariable Integer id) {
        Usuario usuario=usuarioService.obtenerPorID(id);
        return ResponseEntity.ok(userMapper.toResponse(usuario)
        );
    }

    @Operation(//son las anotaciones para swagger y poder integrar informacion a los endpoint para la documentacion
            summary = "Crear usuario",
            description = "Registra un nuevo usuario en el sistema"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno")
    })
    @PostMapping(path = "/crearUsuario")
    public ResponseEntity <UserResponseDto> guardarUsuario(@Valid @RequestBody UserRequestDto usuario) {
        Usuario usuarioRequest = usuarioService.guardarUsuario(userMapper.toEntity(usuario));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userMapper.toResponse(usuarioRequest));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity <UserResponseDto> eliminarUsuario(@PathVariable Integer id) {

        Usuario usuarioEncontrado= usuarioService.eliminarUsuario(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(userMapper.toResponse(usuarioEncontrado));
    }

}
