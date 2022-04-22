package com.doantotnghiep.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.sql.Timestamp;

@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "bill")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bill extends BaseModel{
    @Column(name = "status")
    private Integer status;

    @Column(name = "buy_date")
    private Timestamp buyDate;

    @Column(name = "discount_percent")
    private Integer discountPercent;

    @Column(name = "price_total")
    private Long priceTotal;

    @Column(name = "coupon_name")
    private String couponName;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}