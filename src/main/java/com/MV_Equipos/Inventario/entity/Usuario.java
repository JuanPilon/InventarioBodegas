package com.MV_Equipos.Inventario.entity;

import com.MV_Equipos.Inventario.enums.TipoRol;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Entity //indica que es una tabla
@Table(name = "usuarios")//indica el nombre de la tabla a la que los parametros van a referir
@Data // va a gestionar los getters setters to strings
@NoArgsConstructor// nos ayudara a generar constructores sin argumentos
@AllArgsConstructor//nos ayuda a contruir constructores con sus argumentos por defecto
@Builder // nos ayuda a la creacion de objetos con facilidad
public class Usuario {


    @Id//indica que sera la llave primaria de la tabla
    @GeneratedValue(strategy = GenerationType.IDENTITY)//indica que se generara un consecutivo de forma automatica
    @Column(name = "ID")//indica que el nombre de la columna en la tabla se llama id
    private Integer id;

    @NotBlank(message = "No puedes dejar el campo nombre vacio")
    @Size(min = 3, max = 80)
    @Column(name = "NAME", nullable = false, length = 80)
    private String name;

    @NotBlank(message = "No puedes dejar el campo username vacio")
    @Size(min = 4, max = 80,message = "Almenos 4 caracteres ")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username Invalido")
    @Column(name = "USERNAME", nullable = false, unique = true, length = 80)
    private String username;

    @NotBlank(message = "No puedes dejar el campo password vacio")
    @Size(min = 8, max = 255,message = "Almenos 8 caracteres ")
    @Column(name = "PASSWORD", nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROL", nullable = false, length = 80)
    private TipoRol rol;

    @Column(name = "R_DATE")
    private Timestamp rDate;

    @PrePersist
    private void prePersist() {
        this.rDate = new Timestamp(System.currentTimeMillis());
    }
}