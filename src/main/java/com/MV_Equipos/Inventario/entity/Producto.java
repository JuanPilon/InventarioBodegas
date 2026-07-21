package com.MV_Equipos.Inventario.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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


    @Column(name = "DESCRIPCION_DEL_PRODUCTO", nullable = false, length = 255)
    private String descripcionDelProducto;


    @Column(name = "CLAVE_GENERAL", nullable = false, length = 120)
    private String claveGeneral;


    @Column(name = "MEDIDA", nullable = false, length = 45)
    private String medida;

    @Column(name="EMBALAJE",nullable = false)
    private String embalaje;

    @Column(name="MARCA",nullable = false)
    private String marca;

    @Column(name = "CATEGORIA", nullable = false, length = 45)
    private String categoria;


    @Column(name = "UNIDAD_MINIMA", nullable = false, length = 45)
    private String unidadMinima;


    @Column(name = "PRECIO_POR_UNIDAD_COMPRA", precision = 10, scale = 2, nullable = false)
    private BigDecimal precioPorUnidadCompra;//preciosion nos indica cuantos digitos tendra ese atributo y scale cuantos seran decimales aunque en sql se usa decimal en springboot se usa bigdecimal para precios.


    @Column(name = "FECHA_DE_COMPRA", nullable = false)
// Al contradio de timestamp donde se va a generar la fecha automaticamente aqui si necesitamos mandar el dato o despues la opcion para poder modificarla
    private LocalDate fechaDeCompra;

    @Column(name = "STOCK", nullable = false)
    private Integer stock;


    @Column(name = "BODEGAS", nullable = false, length = 45)
    private String bodegas;

    @Lob//Indica de joa que este campo tendra datos grandes como puede ser un texto
    @Column(name = "NOTAS_GENERALES", columnDefinition = "TEXT")
    private String notasGenerales;

    @Column(name = "C_DATE")
    private Timestamp cDate;

    @PrePersist// esta anotacion permite crear el dato del date antes del guardado para que no mande nulo
    public void prePersist() {
        this.cDate = new Timestamp(System.currentTimeMillis());
    }


}
