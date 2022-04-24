package com.doantotnghiep.demo.service.impl;

import com.doantotnghiep.demo.dto.request.admin.AddSizeRequest;
import com.doantotnghiep.demo.dto.response.admin.SizeDetailResponse;
import com.doantotnghiep.demo.entity.Product;
import com.doantotnghiep.demo.entity.Size;
import com.doantotnghiep.demo.mapper.SizeMapper;
import com.doantotnghiep.demo.repository.ProductRepository;
import com.doantotnghiep.demo.repository.SizeRepository;
import com.doantotnghiep.demo.service.SizeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SizeServiceImpl implements SizeService {

    private final SizeRepository sizeRepository;
    private final ProductRepository productRepository;
    private final SizeMapper sizeMapper;

    @Override
    public void addSize(AddSizeRequest addSizeRequest){
        Product product = productRepository.getOne(addSizeRequest.getProductId());
        if(product == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Không tìm thấy product theo id truyền vào");
        }

        Size size = Size.builder()
                .name(addSizeRequest.getName())
                .quantity(addSizeRequest.getQuantity())
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .updatedAt(new Timestamp(System.currentTimeMillis()))
                .isDeleted(false)
                .product(product).build();

        sizeRepository.save(size);
    }

    @Override
    public void deleteSize(Long sizeId){
        Size size = sizeRepository.getOne(sizeId);

        if(size == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Không tìm thấy size theo id truyền vào");
        }else {
            sizeRepository.deleteById(sizeId);
        }
    }

    @Override
    public List<SizeDetailResponse> listSizeByProductId(Long productId){
        List<Size> list = sizeRepository.getListSizeByproductId(productId);
        List<SizeDetailResponse> response = new ArrayList<>();
        for (Size size: list) {
            response.add(sizeMapper.toListDTO(size));
        }

        return response;
    }


}
