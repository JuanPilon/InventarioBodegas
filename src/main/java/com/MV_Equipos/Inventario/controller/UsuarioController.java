package com.MV_Equipos.Inventario.controller;

import com.MV_Equipos.Inventario.Dto.UserMapper;
import com.MV_Equipos.Inventario.Dto.UserRequestDto;
import com.MV_Equipos.Inventario.Dto.UserResponseDto;
import com.MV_Equipos.Inventario.entity.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.MV_Equipos.Inventario.service.UsuarioService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/usuario")
@CrossOrigin
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UserMapper userMapper;

    @GetMapping(path = "/consultar")
    public List<UserResponseDto> mostrarUsuarios() {
        List<Usuario> listaUsuarios=usuarioService.obtenerUsuarios();
        List<UserResponseDto> listaFinal= new ArrayList<>();
        for(Usuario usuario:listaUsuarios){
            UserResponseDto nuevaEntrada=userMapper.toResponse(usuario);
            listaFinal.add(nuevaEntrada);
        }
        return listaFinal;
    }

    @GetMapping(path = "/buscar/{id}")
    public UserResponseDto obtenerPorID(@PathVariable Integer id) {
        Usuario usuario=usuarioService.obtenerPorID(id);
        return userMapper.toResponse(usuario);
    }

    @PostMapping(path = "/crearUsuario")
    public UserResponseDto guardarUsuario(@Valid @RequestBody UserRequestDto usuario) {
        Usuario usuarioRequest = usuarioService.guardarUsuario(userMapper.toEntity(usuario));

        return userMapper.toResponse(usuarioRequest);
    }

    @DeleteMapping("/eliminar/{id}")
    public UserResponseDto eliminarUsuario(@PathVariable Integer id) {

        Usuario usuarioEncontrado= usuarioService.eliminarUsuario(id);
        return userMapper.toResponse(usuarioEncontrado);
    }

}
