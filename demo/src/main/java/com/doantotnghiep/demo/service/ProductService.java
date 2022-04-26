package com.doantotnghiep.demo.service;


import com.doantotnghiep.demo.dto.request.admin.AddProductRequest;
import com.doantotnghiep.demo.dto.request.admin.ModifiedProductRequest;
import com.doantotnghiep.demo.dto.response.admin.ProductDetailResponse;
import com.doantotnghiep.demo.dto.response.admin.ProductListResponse;
import org.springframework.data.domain.Pageable;


public interface ProductService {
    void addProduct(AddProductRequest addProductRequest);

    void modifiedProduct(Long id, ModifiedProductRequest modifiedProductRequest);

    ProductDetailResponse getProductDetail(Long id);

    void deleteProduct(Long id);

    ProductListResponse getListProduct(String productName, Integer sortBy, Pageable pageable);

    ProductListResponse getListProductHomeBestSeller();

    ProductListResponse getListProductForUser(String productName, Integer branch, Integer gender, Integer style, Integer color, Integer material, Integer tech, Integer sortBy, Pageable pageable);
}
