package com.MV_Equipos.Inventario.entity;
import com.MV_Equipos.Inventario.enums.TipoMovimiento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Entity
@Table(name = "movimientos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Movimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne//indicando que muchos movimientos pertenecen a productos
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
//inidicando que el nombre de esta columna estara relacionada con la columnda perteneciente al objeto de abajo
    private Producto productoId;

    @ManyToOne//indica que muchos movimientos seran realizados por usuarios
    @JoinColumn(name = "USER_ID", nullable = false)
//indicamos que la columna mencionada se relacionara con la columna del obejeto de abajo
    private Usuario userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE_OF_MOVEMENT", nullable = false)
    private TipoMovimiento tipoMovimiento;

    @Column(name = "AMOUNT", nullable = false)
    private Integer cantidad;

    @Column(name = "STOCK", nullable = false)
    private Integer stockFinal;

    @Lob
    @Column(name = "COMMENTS", columnDefinition = "TEXT")
    private String comentarios;

    @Column(name = "MOVEMENT_DAY")
    private Timestamp mDay;

    @PrePersist// esta anotacion permite crear el dato del date antes del guardado para que no mande nulo
    public void prePersist() {
        this.mDay = new Timestamp(System.currentTimeMillis());
    }


}
