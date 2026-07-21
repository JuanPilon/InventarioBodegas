package com.MV_Equipos.Inventario.Dto;

import com.MV_Equipos.Inventario.entity.Producto;
import org.springframework.stereotype.Component;


@Component
public class ProductoMapper {
    public Producto toEntityDto(ProductoRequestDto productoRequestDto) {

        return Producto.builder()
                .descripcionDelProducto(productoRequestDto.getDescripcionDelProducto())
                .claveGeneral(productoRequestDto.getClaveGeneral())
                .medida(productoRequestDto.getMedida())
                .marca(productoRequestDto.getMarca())
                .embalaje(productoRequestDto.getEmbalaje())
                .categoria(productoRequestDto.getCategoria())
                .unidadMinima(productoRequestDto.getUnidadMinima())
                .precioPorUnidadCompra(productoRequestDto.getPrecioPorUnidadCompra())
                .fechaDeCompra(productoRequestDto.getFechaDeCompra())
                .stock(productoRequestDto.getStock())
                .bodegas(productoRequestDto.getBodegas())
                .notasGenerales(productoRequestDto.getNotasGenerales())
                .build();
    }

    public ProductoResponseDto toResponseDto(Producto producto) {
        return  ProductoResponseDto.builder()
                .id(producto.getId())
                .descripcionDelProducto(producto.getDescripcionDelProducto())
                .claveGeneral(producto.getClaveGeneral())
                .medida(producto.getMedida())
                .categoria(producto.getCategoria())
                .marca(producto.getMarca())
                .embalaje(producto.getEmbalaje())
                .unidadMinima(producto.getUnidadMinima())
                .precioPorUnidadCompra(producto.getPrecioPorUnidadCompra())
                .fechaDeCompra(producto.getFechaDeCompra())
                .stock(producto.getStock())
                .bodegas(producto.getBodegas())
                .notasGenerales(producto.getNotasGenerales())
                .cDate(producto.getCDate())
                .build();

    }
    public Producto toEntityDtoEdit(ProductEditRequestDto productoRequestDto) {

        return Producto.builder()
                .descripcionDelProducto(productoRequestDto.getDescripcionDelProducto())
                .claveGeneral(productoRequestDto.getClaveGeneral())
                .medida(productoRequestDto.getMedida())
                .categoria(productoRequestDto.getCategoria())
                .unidadMinima(productoRequestDto.getUnidadMinima())
                .marca(productoRequestDto.getMarca())
                .embalaje(productoRequestDto.getEmbalaje())
                .precioPorUnidadCompra(productoRequestDto.getPrecioPorUnidadCompra())
                .fechaDeCompra(productoRequestDto.getFechaDeCompra())
                .stock(productoRequestDto.getStock())
                .bodegas(productoRequestDto.getBodegas())
                .notasGenerales(productoRequestDto.getNotasGenerales())
                .build();
    }

}
