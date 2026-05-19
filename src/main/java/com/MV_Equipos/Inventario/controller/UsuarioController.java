package com.MV_Equipos.Inventario.controller;

import com.MV_Equipos.Inventario.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.MV_Equipos.Inventario.service.UsuarioService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/usuario")
@CrossOrigin
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping(path = "/consultar")
    public List<Usuario> mostrarUsuarios() {
        return usuarioService.obtenerUsuarios();
    }

    @GetMapping(path = "/buscar/{id}")
    public Optional<Usuario> obtenerPorID(@PathVariable Integer id) {
        return usuarioService.obtenerPorID(id);
    }

    @PostMapping(path = "/crearUsuario")
    public Usuario guardarUsuario(@RequestBody Usuario usuario) {
        return usuarioService.guardarUsuario(usuario);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminarUsuario(@PathVariable Integer id) {
        usuarioService.eliminarUsuario(id);
    }

}
