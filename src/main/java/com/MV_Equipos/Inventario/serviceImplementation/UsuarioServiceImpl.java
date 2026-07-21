package com.MV_Equipos.Inventario.serviceImplementation;

import com.MV_Equipos.Inventario.Exception.RecursoNoEncontradoException;
import com.MV_Equipos.Inventario.Exception.ValidacionException;
import com.MV_Equipos.Inventario.config.SecurityConfig;
import com.MV_Equipos.Inventario.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.MV_Equipos.Inventario.repository.UserRepository;
import com.MV_Equipos.Inventario.service.UsuarioService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service//le indica a spring que aqui habra logica de negocio

public class UsuarioServiceImpl implements UsuarioService {
    @Autowired//sirve para la inyeccion de dependencias trae un los metodos de la clase elegida instanciando un metodo
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @Override//nos idica que estamos trabajando con un metodo modificado del repositorio
    public Usuario guardarUsuario(Usuario usuario) {

        usuario.setName(normalizarTexto(usuario.getName()));
        usuario.setUsername(normalizarTexto(usuario.getUsername()));//El metodo elimina espacios y cambia automaticamente a mayusculas para el guardado del usuario
        validarUsuarioExistente(usuario.getUsername());//permite la revision de usuario ya existente
        validarPassword(usuario);//Verifica que la contrasena contenga una mayuscula y un numero
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return userRepository.save(usuario);

    }

    @Transactional(readOnly = true)
    @Override
    public List<Usuario> obtenerUsuarios() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Usuario obtenerPorID(Integer id) {
        validarId(id);

        return userRepository.findById(id).orElseThrow(() ->new RecursoNoEncontradoException("Usuario no encontrado"));
    }

    @Transactional(readOnly = true)
    @Override
    public Usuario obtenerPorUsername(String username) {

        Usuario usuarioEncontrado= userRepository.findByUsername(normalizarTexto(username));
        if(usuarioEncontrado==null){
            throw new RecursoNoEncontradoException("Usuario no encontrado");
        }

        return usuarioEncontrado;
    }

    @Transactional
    @Override
    public Usuario eliminarUsuario(Integer id) {

        validarId(id);
        if(!userRepository.existsById(id)){
            throw new RecursoNoEncontradoException("El usuario a elminar no existe ");
        }
        Usuario usuarioEliminado= obtenerPorID(id);
        userRepository.deleteById(id);
        return usuarioEliminado;

    }


    //Funciones de validacion en los metodos

    private void validarUsuarioExistente(String username){
        if(userRepository.findByUsername(username)!=null){
            throw new ValidacionException("Usuario existente");
        }

    }

    private void validarPassword(Usuario usuario){

        if(!usuario.getPassword()
                .matches("^(?=.*[A-Z])(?=.*\\d).+$")){

            throw new ValidacionException(
                    "La contraseña debe contener al menos una mayúscula y un número");
        }
    }
    private String normalizarTexto(String username){

        if(username == null || username.isBlank()){
            throw new ValidacionException(
                    "El campo no puede estar vacio");
        }
        return username.trim().toUpperCase();
    }

    private void validarId(Integer id){

        if(id == null || id <= 0){
            throw new ValidacionException(
                    "Los id deben ser mayores a 0");
        }
    }



}
