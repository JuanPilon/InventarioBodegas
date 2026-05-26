package com.MV_Equipos.Inventario.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

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

    @Column(name = "PRECIO_POR_UNIDAD", precision = 10, scale = 2, nullable = false)
    private BigDecimal precioPorUnidad;//preciosion nos indica cuantos digitos tendra ese atributo y scale cuantos seran decimales aunque en sql se usa decimal en springboot se usa bigdecimal para precios.

    @Column(name = "FECHA_DE_PRECIO", nullable = false)
// Al contradio de timestamp donde se va a generar la fecha automaticamente aqui si necesitamos mandar el dato o despues la opcion para poder modificarla
    private LocalDate fechaDePrecio;


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
