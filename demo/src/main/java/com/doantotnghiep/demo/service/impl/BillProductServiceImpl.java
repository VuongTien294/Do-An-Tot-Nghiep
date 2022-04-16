package com.doantotnghiep.demo.service.impl;

import com.doantotnghiep.demo.dto.request.user.AddBillProductDetailRequest;
import com.doantotnghiep.demo.dto.request.user.AddBillProductRequest;
import com.doantotnghiep.demo.entity.Bill;
import com.doantotnghiep.demo.entity.BillProduct;
import com.doantotnghiep.demo.entity.Product;
import com.doantotnghiep.demo.repository.BillProductRepository;
import com.doantotnghiep.demo.repository.BillRepository;
import com.doantotnghiep.demo.repository.ProductRepository;
import com.doantotnghiep.demo.service.BillProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BillProductServiceImpl implements BillProductService {
    private final ProductRepository productRepository;
    private final BillRepository billRepository;
    private final BillProductRepository billProductRepository;

    @Override
    public void addBillProduct(AddBillProductRequest addBillProductRequest){
        for (AddBillProductDetailRequest billProductRequest : addBillProductRequest.getList()){

            Product product = productRepository.getOne(billProductRequest.getProductId());

            Bill bill = billRepository.getOne(billProductRequest.getBillId());

            BillProduct billProduct = BillProduct.builder()
                    .unitPrice(billProductRequest.getUnitPrice())
                    .quantity(billProductRequest.getQuantity())
                    .product(product)
                    .bill(bill)
                    .build();

            billProductRepository.save(billProduct);
        }
    }

}
