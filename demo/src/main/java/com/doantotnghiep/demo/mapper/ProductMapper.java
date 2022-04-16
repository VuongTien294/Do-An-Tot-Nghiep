package com.doantotnghiep.demo.mapper;

import com.doantotnghiep.demo.dto.response.admin.ProductDetailResponse;
import com.doantotnghiep.demo.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper {
    public ProductDetailResponse toListDTO(Product product) {
        ProductDetailResponse dto = new ProductDetailResponse();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setTotalQuantity(product.getTotalQuantity());
        dto.setSoldQuantity(product.getSoldQuantity());
        dto.setManufacturer(product.getManufacturer());
        dto.setDescription(product.getDescription());
        dto.setTotalRating(product.getTotalRating());
        dto.setTotalStar(product.getTotalStar());
        dto.setImage(product.getImage());

        return dto;
    }
}
