package com.doantotnghiep.demo.dto.request.user;

import com.doantotnghiep.demo.entity.User;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.sql.Timestamp;
import java.util.List;

@Data
public class BuyRequest {
    private Integer discountPersent;
    private String couponName;
    private Long userId;

    private List<AddBillProductDetailRequest> listBillProducts;
}
