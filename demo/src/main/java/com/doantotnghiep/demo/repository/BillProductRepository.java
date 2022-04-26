package com.doantotnghiep.demo.repository;

import com.doantotnghiep.demo.entity.BillProduct;
import com.doantotnghiep.demo.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillProductRepository extends JpaRepository<BillProduct,Long>, JpaSpecificationExecutor<BillProduct> {

    List<BillProduct> getBillProductByBillId(Long billId);
}
