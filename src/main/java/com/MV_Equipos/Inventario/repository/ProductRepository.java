package com.MV_Equipos.Inventario.repository;

import com.MV_Equipos.Inventario.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Producto, Integer> {

    Optional<Producto> findByClaveGeneral(String claveGeneral);

    List<Producto> findByNombreContainingIgnoreCaseOrClaveGeneralContainingIgnoreCaseOrBodegaContainingIgnoreCaseOrTipoContainingIgnoreCase(
            String nombre,
            String claveGeneral,
            String bodega,
            String tipo

    );

}
