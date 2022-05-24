package com.doantotnghiep.demo.service.impl;

import com.doantotnghiep.demo.dto.response.admin.*;
import com.doantotnghiep.demo.dto.response.user.BillDashBoardResponse;
import com.doantotnghiep.demo.dto.response.user.BillProductDetailResponse;
import com.doantotnghiep.demo.entity.Bill;
import com.doantotnghiep.demo.entity.BillProduct;
import com.doantotnghiep.demo.entity.Coupon;
import com.doantotnghiep.demo.entity.Product;
import com.doantotnghiep.demo.mapper.BillProductMapper;
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
    private final BillProductMapper billProductMapper;

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
            countProductSelled = countProductSelled + listProduct.get(i).getSoldQuantity();
        }

        Map<String,Long> map = new HashMap<>();
        map.put("UserEnabled" , countUserEnabled);
        map.put("UserBan" , countUserBan);
        map.put("countProductTotal" , countProductTotal);
        map.put("countProductSelled" , countProductSelled);
        return  map;
    }

    @Override
    public DashBoardBodyResponse getListDashBoard(Integer month, Integer year, Integer sortBy, Pageable pageable){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Bill> cq = cb.createQuery(Bill.class);
        Root<Bill> root = cq.from(Bill.class);
        List<Predicate> listPredicate = new ArrayList<>();

        listPredicate.add(cb.equal((root.get("month")), month));

        listPredicate.add(cb.equal((root.get("year")), year));

        Path<Object> sort = null;
        Order order = null;

        if (sortBy != null) {
            switch (sortBy) {
                case 0:
                    sort = root.get("updatedAt");
                    order = cb.desc(sort);
                    break;
                case 1:
                    sort = root.get("updatedAt");
                    order = cb.asc(sort);
            }
        }

        Predicate[] finalPredicate = new Predicate[listPredicate.size()];
        listPredicate.toArray(finalPredicate);

        TypedQuery<Bill> query = em.createQuery(cq.select(root).where(cb.and(finalPredicate)).orderBy(order));
        query.setMaxResults(pageable.getPageSize());
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Bill> countRoot = countQuery.from(Bill.class);

        List<BillDashBoardResponse> responseDTOS = new ArrayList<>();
        query.getResultList().forEach(bill -> responseDTOS.add(billProductMapper.toDashBoard(bill)));

        Double totalPrice = Double.valueOf(0);

        for(int i = 0;i< responseDTOS.size();i++){
            totalPrice = totalPrice + responseDTOS.get(i).getPriceTotal().doubleValue();
        }

        DashBoardBodyResponse dashBoardBodyResponse = new DashBoardBodyResponse();
//        dashBoardBodyResponse.setSoldQuantity(soldQuantity);
        dashBoardBodyResponse.setTotalPrice(totalPrice);
//        dashBoardBodyResponse.setList(responseDTOS);


        return dashBoardBodyResponse;

    }

//    public DashBoardBodyResponse getListDashBoard(Integer month, Integer year, Integer sortBy, Pageable pageable){
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
//        List<BillProductDetailResponse> responseDTOS = new ArrayList<>();
//        query.getResultList().forEach(bill -> responseDTOS.add(billProductMapper.toListDTO(bill)));
//
//        Long soldQuantity = Long.valueOf(0);
//        Long totalPrice = Long.valueOf(0);
//
//        for(int i = 0;i< responseDTOS.size();i++){
//            soldQuantity = soldQuantity + responseDTOS.get(i).getQuantity();
//            totalPrice = totalPrice + responseDTOS.get(i).getUnitPrice()*responseDTOS.get(i).getQuantity();
//        }
//
//        DashBoardBodyResponse dashBoardBodyResponse = new DashBoardBodyResponse();
//        dashBoardBodyResponse.setSoldQuantity(soldQuantity);
//        dashBoardBodyResponse.setTotalPrice(totalPrice);
//        dashBoardBodyResponse.setList(responseDTOS);
//
//
//        return dashBoardBodyResponse;
//
//    }


}
