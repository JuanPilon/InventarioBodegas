package com.MV_Equipos.Inventario.Dto;

import com.MV_Equipos.Inventario.entity.Movimiento;
import com.MV_Equipos.Inventario.entity.Producto;
import com.MV_Equipos.Inventario.entity.Usuario;
import com.MV_Equipos.Inventario.enums.TipoMovimiento;
import org.springframework.stereotype.Component;


@Component
public class MovimientoMapper {
    public Movimiento toEntity(MovimientoRequestDto dto) {
        return  Movimiento.builder()
                .productoId(dto.getProductoId())
                .userId(dto.getUserId())
                .tipoMovimiento(dto.getTipoMovimiento())
                .cantidad(dto.getCantidad())
                .stockFinal(dto.getStockFinal())
                .nombreArchivo(dto.getNombreArchivo())
                .rutaArchivo(dto.getRutaArchivo())
                .tipoArchivo(dto.getTipoArchivo())
                .comentarios(dto.getComentarios())
                .build();


    }

    public MovimientoResponseDto toResponseDto(Movimiento entity) {
        System.out.println("NAME: " + entity.getUserId().getName());
        System.out.println("ROL: " + entity.getUserId().getRol());
        System.out.println("USERNAME: " + entity.getUserId().getUsername());


        return MovimientoResponseDto.builder()
                .idMovimiento(entity.getId())
                .productoId(entity.getProductoId().getId())
                .userId(entity.getUserId().getId())
                .usuario(entity.getUserId().getName())
                .tipoMovimiento(entity.getTipoMovimiento())
                .producto(entity.getProductoId().getNombre())
                .cantidad(entity.getCantidad())
                .stockFinal(entity.getStockFinal())
                .nombreArchivo(entity.getNombreArchivo())
                .rutaArchivo(entity.getRutaArchivo())
                .tipoArchivo(entity.getTipoArchivo())
                .comentarios(entity.getComentarios())
                .build();

    }


}
