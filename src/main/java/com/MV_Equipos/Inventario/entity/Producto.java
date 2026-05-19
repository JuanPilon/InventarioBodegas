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

    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @Column(name = "CODE", nullable = false, length = 120)
    private String code;

    @Column(name = "SKU", nullable = false, unique = true, length = 30)
    private String sku;

    @Column(name = "PRICE", nullable = false, precision = 10, scale = 2)//precision nos indica cuantos enteros tendra y el scale es cuantos digitos de ahi seran decimales
    private BigDecimal price;

    @Column(name = "STOCK", nullable = false)
    private Integer stock;

    @Column(name = "LOCATION", nullable = false)
    private String location;

    @Lob//Indica de joa que este campo tendra datos grandes como puede ser un texto
    @Column(name = "NOTES", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "C_DATE")
    private Timestamp cDate;
    @PrePersist// esta anotacion permite crear el dato del date antes del guardado para que no mande nulo
    public void prePersist() {
        this.cDate = new Timestamp(System.currentTimeMillis());
    }


}
