package com.MV_Equipos.Inventario.service;

import com.MV_Equipos.Inventario.entity.Usuario;

import java.util.List;
import java.util.Optional;

//Se genero un usuarioservice como una interfaz para declarar la estructura de lo metodos para su uso posterior no tiene nivel de
//proteccion por que java lo interpreta como public abstract
public interface UsuarioService {
    Usuario guardarUsuario(Usuario usuario);


    List<Usuario> obtenerUsuarios();

    Optional<Usuario> obtenerPorID(Integer id);

    Optional<Usuario> obtenerPorUsername(String username);

    void eliminarUsuario(Integer id);

}
