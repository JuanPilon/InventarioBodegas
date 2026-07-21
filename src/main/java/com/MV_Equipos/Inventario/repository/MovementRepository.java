package com.MV_Equipos.Inventario.repository;

import com.MV_Equipos.Inventario.entity.Movimiento;
import com.MV_Equipos.Inventario.enums.TipoMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovementRepository extends JpaRepository<Movimiento,Integer>//Indica que extenderemos los metodos de JPA y que estaran involucrado objetos del tipo Movimiento y enteros
{
    List<Movimiento> findByProductoId_Id(Integer productoId);
    List<Movimiento>findByTipoMovimiento(TipoMovimiento movimiento);
    List<Movimiento> findAllByOrderByMDayDesc();
}
