package com.MV_Equipos.Inventario.serviceImplementation;

import com.MV_Equipos.Inventario.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.MV_Equipos.Inventario.repository.UserRepository;
import com.MV_Equipos.Inventario.service.UsuarioService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service//le indica a spring que aqui habra logica de negocio

public class UsuarioServiceImpl implements UsuarioService {
    @Autowired//sirve para la inyeccion de dependencias trae un los metodos de la clase elegida instanciando un metodo
    private UserRepository userRepository;

    @Transactional
    @Override//nos idica que estamos trabajando con un metodo modificado del repositorio
    public Usuario guardarUsuario(Usuario usuario) {

        usuario.setUsername(normalizarUsuario(usuario.getUsername()));//El metodo elimina espacios y cambia automaticamente a mayusculas para el guardado del usuario
        validarUsuarioExistente(usuario.getUsername());//permite la revision de usuario ya existente
        validarPassword(usuario);//Verifica que la contrasena contenga una mayuscula y un numero
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

        return userRepository.findById(id).orElseThrow(() ->new RuntimeException("Usuario no encontrado"));
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Usuario> obtenerPorUsername(String username) {



        return userRepository.findByUsername(normalizarUsuario(username));
    }

    @Transactional
    @Override
    public void eliminarUsuario(Integer id) {

        validarId(id);
        if(!userRepository.existsById(id)){
            throw new RuntimeException("El usuario a elminar no existe ");
        }
        userRepository.deleteById(id);

    }


    //Funciones de validacion en los metodos

    private void validarUsuarioExistente(String username){
        if(userRepository.findByUsername(username).isPresent()){
            throw new RuntimeException("El nombre de usuario ya existe favor de usar uno que no exista");
        }

    }

    private void validarPassword(Usuario usuario){

        if(!usuario.getPassword()
                .matches("^(?=.*[A-Z])(?=.*\\d).+$")){

            throw new RuntimeException(
                    "La contraseña debe contener al menos una mayúscula y un número");
        }
    }
    private String normalizarUsuario(String username){

        return username.trim().toUpperCase();
    }

    private void validarId(Integer id){

        if(id == null || id <= 0){
            throw new RuntimeException(
                    "Los id deben ser mayores a 0");
        }
    }



}
