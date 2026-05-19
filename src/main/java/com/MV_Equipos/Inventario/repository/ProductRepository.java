package com.MV_Equipos.Inventario.repository;

import com.MV_Equipos.Inventario.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Producto,Integer> {

    Optional<Producto> findByCode(String code);
    List<Producto> findByNameContainingIgnoreCaseOrCodeContainingIgnoreCaseOrSkuContainingIgnoreCaseOrPriceOrLocationContainingIgnoreCase(
            String name,
            String code,
            String sku,
            BigDecimal price,
            String location
    );

}
