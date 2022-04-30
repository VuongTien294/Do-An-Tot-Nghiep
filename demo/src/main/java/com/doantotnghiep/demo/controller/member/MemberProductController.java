package com.doantotnghiep.demo.controller.member;

import com.doantotnghiep.demo.dto.response.admin.CouponDetailResponse;
import com.doantotnghiep.demo.dto.response.admin.ProductDetailResponse;
import com.doantotnghiep.demo.dto.response.admin.ProductListResponse;
import com.doantotnghiep.demo.service.ProductService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class MemberProductController {
    private final ProductService productService;

    @ApiOperation("Api hiển thị tất cả product lên trang hôm.Nếu số product < 16 FE hiển thị hết.Nếu >= 16 thì chỉ hiển thị 16 product thôi")
    @GetMapping("/product/home")
    public ProductListResponse getBestSellerProduct(
    ){
        return productService.getListProductHomeBestSeller();
    }

    @ApiOperation("Api filter product cho khách hàng")
    @GetMapping("/product/filter")
    public ProductListResponse getProductList(
            @RequestParam(name = "product_name",required = false) String productName,
            @RequestParam(name = "branch",required = false) Integer branch,
            @RequestParam(name = "gender",required = false) Integer gender,
            @RequestParam(name = "style",required = false) Integer style,
            @RequestParam(name = "color",required = false) Integer color,
            @RequestParam(name = "material",required = false) Integer material,
            @RequestParam(name = "technology",required = false) Integer technology,
            @RequestParam(name = "sort",required = true,defaultValue = "0") Integer sort,
            Pageable pageable
    ) {
        return productService.getListProductForUser(productName,branch,gender,style,color,material,technology,sort,pageable);
    }

    @ApiOperation("Api get detail product cho khách hàng")
    @GetMapping("/product/{id}")
    public ProductDetailResponse getProductDetail(
            @PathVariable(required = true) Long id
    ) {
        return productService.getProductDetail(id);
    }

}
