package com.MV_Equipos.Inventario.repository;

import com.MV_Equipos.Inventario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository// Indica al spring que esta interface tendra metodos que manejaran datos de la base
public interface UserRepository extends JpaRepository<Usuario, Integer> { //se usa extend para heredar los metodos y atributos de jpa
    //Me creo un metodo mixto para revisar la existencia de un usuario dependiendo de su nombre
    Usuario findByUsername(String username);

}
