package com.MV_Equipos.Inventario.service;

import com.MV_Equipos.Inventario.entity.Usuario;

import java.util.List;

//Se genero un usuarioservice como una interfaz para declarar la estructura de lo metodos para su uso posterior no tiene nivel de
//proteccion por que java lo interpreta como public abstract
public interface UsuarioService {
    Usuario guardarUsuario(Usuario usuario);


    List<Usuario> obtenerUsuarios();

    Usuario obtenerPorID(Integer id);

    Usuario obtenerPorUsername(String username);

    void eliminarUsuario(Integer id);

}
