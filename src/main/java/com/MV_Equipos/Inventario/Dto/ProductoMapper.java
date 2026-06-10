package com.MV_Equipos.Inventario.Dto;

import com.MV_Equipos.Inventario.entity.Producto;
import org.springframework.stereotype.Component;


@Component
public class ProductoMapper {
    public Producto toEntityDto(ProductoRequestDto productoRequestDto) {

        return Producto.builder()
                .nombre(productoRequestDto.getNombre())
                .claveGeneral(productoRequestDto.getClaveGeneral())
                .tamano(productoRequestDto.getTamano())
                .tipo(productoRequestDto.getTipo())
                .nomenclatura(productoRequestDto.getNomenclatura())
                .precioPorUnidad(productoRequestDto.getPrecioPorUnidad())
                .fechaDePrecio(productoRequestDto.getFechaDePrecio())
                .stock(productoRequestDto.getStock())
                .bodega(productoRequestDto.getBodega())
                .notas(productoRequestDto.getNotas())
                .build();
    }

    public ProductoResponseDto toResponseDto(Producto producto) {
        return  ProductoResponseDto.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .claveGeneral(producto.getClaveGeneral())
                .tamano(producto.getTamano())
                .tipo(producto.getTipo())
                .nomenclatura(producto.getNomenclatura())
                .precioPorUnidad(producto.getPrecioPorUnidad())
                .fechaDePrecio(producto.getFechaDePrecio())
                .stock(producto.getStock())
                .bodega(producto.getBodega())
                .notas(producto.getNotas())
                .build();

    }

}
