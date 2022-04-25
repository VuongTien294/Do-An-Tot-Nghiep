package com.doantotnghiep.demo.controller.admin;

import com.doantotnghiep.demo.dto.request.admin.AddProductRequest;
import com.doantotnghiep.demo.dto.request.admin.ModifiedProductRequest;
import com.doantotnghiep.demo.dto.response.admin.ProductDetailResponse;
import com.doantotnghiep.demo.dto.response.admin.ProductListResponse;
import com.doantotnghiep.demo.service.ProductService;
import com.doantotnghiep.demo.service.impl.CoudinaryService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedHashMap;

@CrossOrigin(origins = "*", maxAge = -1)
@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductService productService;
    private final CoudinaryService cloudinaryGifService;

    @ApiOperation("Api cho admin tạo mới 1 product")
    @PostMapping("/admin/product/add")
    public void addProduct(
            @RequestBody(required = true) AddProductRequest addProductRequest
    ) {
        productService.addProduct(addProductRequest);
    }

    @ApiOperation("Api xem list product cho admin.")
    @GetMapping("/admin/product/list")
    public ProductListResponse getProductList(
            @RequestParam(name = "product_name",required = false) String productName,
            @RequestParam(name = "sort",required = true,defaultValue = "0") Integer sort,
            Pageable pageable
    ) {
        return productService.getListProduct(productName,sort,pageable);
    }

    @ApiOperation("Api chỉnh sửa product cho admin")
    @PutMapping("/admin/product/modified/{id}")
    public void modifiedProduct(
            @RequestBody(required = true) ModifiedProductRequest productRequest,
            @PathVariable Long id
    ) {
        productService.modifiedProduct(id,productRequest);
    }

    @ApiOperation("Api lấy chi tiết 1 product cho admin")
    @GetMapping("/admin/product/{id}")
    public ProductDetailResponse getProductDetail(
            @PathVariable(required = true) Long id
    ){
        return productService.getProductDetail(id);
    }

    @ApiOperation("Api xóa 1 product cho admin")
    @GetMapping("/admin/product/delete")
    public void deleteProduct(
            @RequestParam(required = true) Long id
    ){
        productService.deleteProduct(id);
    }

    @ApiOperation("Api trả về link ảnh cho FE")
    @PostMapping("/admin/product/image")
    public String upLoad(@RequestParam("file") MultipartFile gifFile){
        return cloudinaryGifService.uploadFile(gifFile);
    }
}
