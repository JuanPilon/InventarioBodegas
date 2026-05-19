package com.MV_Equipos.Inventario.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "productos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    @Column(name = "CLAVE_GENERAL", nullable = false, length = 120)
    private String claveGeneral;

    @Column(name = "TAMANO", nullable = false, length = 45)
    private String tamano;


    @Column(name = "TIPO", nullable = false, length = 45)
    private String tipo;
    @Column(name = "NOMENCLATURA", nullable = false, length = 45)
    private String nomenclatura;

    @Column(name = "STOCK", nullable = false)
    private Integer stock;

    @Column(name = "Bodega", nullable = false, length = 45)
    private String bodega;

    @Lob//Indica de joa que este campo tendra datos grandes como puede ser un texto
    @Column(name = "NOTAS", columnDefinition = "TEXT")
    private String notas;

    @Column(name = "C_DATE")
    private Timestamp cDate;

    @PrePersist// esta anotacion permite crear el dato del date antes del guardado para que no mande nulo
    public void prePersist() {
        this.cDate = new Timestamp(System.currentTimeMillis());
    }


}
