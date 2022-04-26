package com.doantotnghiep.demo.service.impl;

import com.doantotnghiep.demo.dto.response.admin.CouponDetailResponse;
import com.doantotnghiep.demo.dto.response.admin.CouponListResponse;
import com.doantotnghiep.demo.dto.response.admin.DashBoardResponse;
import com.doantotnghiep.demo.dto.response.admin.GetArrayResponse;
import com.doantotnghiep.demo.entity.Bill;
import com.doantotnghiep.demo.entity.BillProduct;
import com.doantotnghiep.demo.entity.Coupon;
import com.doantotnghiep.demo.entity.Product;
import com.doantotnghiep.demo.mapper.ProductMapper;
import com.doantotnghiep.demo.repository.ProductRepository;
import com.doantotnghiep.demo.repository.UserRepository;
import com.doantotnghiep.demo.service.DashBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class DashBoardServiceImpl implements DashBoardService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @PersistenceContext
    EntityManager em;

    @Override
    public Map<String,Long> getHeaderDashBoard(){
         Long countUserEnabled = userRepository.countUserByEnabled(true);
         Long countUserBan = userRepository.countUserByEnabled(false);
         Long countProductTotal = Long.valueOf(0);
         Long countProductSelled = Long.valueOf(0);

        List<Product> listProduct = productRepository.findAll();
        for(int i = 0;i<listProduct.size();i++){
            countProductTotal = countProductTotal + listProduct.get(i).getTotalQuantity();
            countProductSelled = countProductTotal + listProduct.get(i).getSoldQuantity();
        }

        Map<String,Long> map = new HashMap<>();
        map.put("UserEnabled" , countUserEnabled);
        map.put("UserBan" , countUserBan);
        map.put("countProductTotal" , countProductTotal);
        map.put("countProductSelled" , countProductSelled);
        return  map;
    }

//    public GetArrayResponse<DashBoardResponse> getListDashBoard(Integer sortBy, Integer month, Integer year, Pageable pageable){
//
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<BillProduct> cq = cb.createQuery(BillProduct.class);
//        Root<BillProduct> root = cq.from(BillProduct.class);
//        List<Predicate> listPredicate = new ArrayList<>();
//
//        listPredicate.add(cb.equal((root.get("month")), month));
//
//        listPredicate.add(cb.equal((root.get("year")), year));
//
//        Path<Object> sort = null;
//        Order order = null;
//
//        if (sortBy != null) {
//            switch (sortBy) {
//                case 0:
//                    sort = root.get("updatedAt");
//                    order = cb.desc(sort);
//                    break;
//                case 1:
//                    sort = root.get("updatedAt");
//                    order = cb.asc(sort);
//            }
//        }
//
//        Predicate[] finalPredicate = new Predicate[listPredicate.size()];
//        listPredicate.toArray(finalPredicate);
//
//        TypedQuery<BillProduct> query = em.createQuery(cq.select(root).where(cb.and(finalPredicate)).orderBy(order));
//        query.setMaxResults(pageable.getPageSize());
//        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
//
//        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
//        Root<BillProduct> countRoot = countQuery.from(BillProduct.class);
//        Long count = em.createQuery(countQuery.select(cb.count(countRoot)).where(cb.and(finalPredicate))).getSingleResult();
//
//        GetArrayResponse<DashBoardResponse> responseDTOS = new GetArrayResponse<>();
//        Integer countSoldQuantity = 0;
//        Long countPriceTotal = Long.valueOf(0);
//
//        List<BillProduct> list = query.getResultList();
//        for(int i = 0;i<list.size();i++){
//            countSoldQuantity = countSoldQuantity + list.get(i).getQuantity();
//            countPriceTotal = countPriceTotal + list.get(i).getUnitPrice()*list.get(i).getQuantity();
//        }
//
//        return responseDTOS;
//    }


}
