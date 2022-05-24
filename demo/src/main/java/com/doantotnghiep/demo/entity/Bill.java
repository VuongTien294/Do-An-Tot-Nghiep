package com.doantotnghiep.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
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
    private BigDecimal priceTotal;

    @Column(name = "coupon_name")
    private String couponName;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "reason_cancel")
    private String reasonCancel;

    @Column(name = "shipper_name")
    private String shipperName;

    @Column(name = "shipper_phone")
    private String shipperPhone;

    @Column(name = "month")
    private Integer month;

    @Column(name = "year")
    private Integer year;
}
