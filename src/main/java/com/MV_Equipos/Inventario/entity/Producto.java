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

    @NotBlank(message = "El camo nombre de prouducto no debe quedar vacio")
    @Size(max = 255)
    @Column(name = "NOMBRE", nullable = false, length = 255)
    private String nombre;

    @NotBlank(message = "El campo clave de prouducto no debe quedar vacio")
    @Size(max = 120)
    @Column(name = "CLAVE_GENERAL", nullable = false, length = 120)
    private String claveGeneral;

    @NotBlank(message = "El campo tamaño no debe quedar vacio")
    @Size(max = 45)
    @Column(name = "TAMANO", nullable = false, length = 45)
    private String tamano;

    @NotBlank(message = "El campo Tipo no debe quedar vacio")
    @Size(max = 45)
    @Column(name = "TIPO", nullable = false, length = 45)
    private String tipo;

    @NotBlank(message = "El campo momenclatura no debe quedar vacio")
    @Size(max = 45)
    @Column(name = "NOMENCLATURA", nullable = false, length = 45)
    private String nomenclatura;

    @NotNull(message = "Precio obligatorio")
    @DecimalMin(value = "0.0",inclusive = false,message = "El precio debe ser mayor a 0")
    @Column(name = "PRECIO_POR_UNIDAD", precision = 10, scale = 2, nullable = false)
    private BigDecimal precioPorUnidad;//preciosion nos indica cuantos digitos tendra ese atributo y scale cuantos seran decimales aunque en sql se usa decimal en springboot se usa bigdecimal para precios.

    @NotNull(message = "Por favor registra la fecha del cambio de precio")
    @PastOrPresent(message = "La fecha no puede ser futura")//Indica que solo se puede registrar una fecha presente o pasada pero nunca futura
    @Column(name = "FECHA_DE_PRECIO", nullable = false)
// Al contradio de timestamp donde se va a generar la fecha automaticamente aqui si necesitamos mandar el dato o despues la opcion para poder modificarla
    private LocalDate fechaDePrecio;

    @NotNull(message = "Stock obligatorio")
    @PositiveOrZero(message = "El stock no puede ser negativo")
    @Column(name = "STOCK", nullable = false)
    private Integer stock;

    @NotBlank(message = "El campo Bodega no debe quedar vacio")
    @Size(max = 45)
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
