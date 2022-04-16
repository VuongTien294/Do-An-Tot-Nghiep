package com.doantotnghiep.demo.repository;

import com.doantotnghiep.demo.entity.BillProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillProductRepository extends JpaRepository<BillProduct,Long> {
}
