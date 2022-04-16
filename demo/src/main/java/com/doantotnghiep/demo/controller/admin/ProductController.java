package com.doantotnghiep.demo.controller.admin;

import com.doantotnghiep.demo.dto.request.admin.AddProductRequest;
import com.doantotnghiep.demo.dto.response.admin.ProductDetailResponse;
import com.doantotnghiep.demo.dto.response.admin.ProductListResponse;
import com.doantotnghiep.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@CrossOrigin(origins = "*", maxAge = -1)
@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/admin/product/add")
    public void addProduct(
            @RequestBody(required = true) AddProductRequest addProductRequest
    ) {
        productService.addProduct(addProductRequest);
    }

    @GetMapping("/admin/product/list")
    public ProductListResponse getProductList(
            @RequestParam(name = "product_name",required = false) String productName,
            @RequestParam(name = "sort",required = true,defaultValue = "0") Integer sort,
            Pageable pageable
    ) {
        return productService.getListProduct(productName,sort,pageable);
    }

    @PutMapping("/admin/product/modified")
    public void modifiedProduct(
            @RequestBody(required = true) AddProductRequest productRequest
    ) {
        productService.modifiedProduct(productRequest);
    }

    @GetMapping("/admin/product/{id}")
    public ProductDetailResponse getProductDetail(
            @PathVariable(required = true) Long id
    ){
        return productService.getProductDetail(id);
    }

    @GetMapping("/admin/product/delete")
    public void deleteProduct(
            @RequestParam(required = true) Long id
    ){
        productService.deleteProduct(id);
    }
}
