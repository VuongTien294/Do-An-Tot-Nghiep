package com.doantotnghiep.demo.repository;

import com.doantotnghiep.demo.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon,Long> {
    Coupon findCouponByCode(String code);

}
