package com.MV_Equipos.Inventario.serviceImplementation;

import com.MV_Equipos.Inventario.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.MV_Equipos.Inventario.repository.UserRepository;
import com.MV_Equipos.Inventario.service.UsuarioService;

import java.util.List;
import java.util.Optional;

@Service//le indica a spring que aqui habra logica de negocio

public class UsuarioServiceImpl implements UsuarioService {
    @Autowired//sirve para la inyeccion de dependencias trae un los metodos de la calse elejida instanaciando un metodo
    private UserRepository userRepository;

    @Override//nos idica que estamos trabajando con un metodo modificado del repositorio
    public Usuario guardarUsuario(Usuario usuario) {
        return userRepository.save(usuario);
    }

    @Override
    public List<Usuario> obtenerUsuarios() {
        return userRepository.findAll();
    }

    @Override
    public Optional<Usuario> obtenerPorID(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<Usuario> obtenerPorUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void eliminarUsuario(Integer id) {
        userRepository.deleteById(id);

    }




}
