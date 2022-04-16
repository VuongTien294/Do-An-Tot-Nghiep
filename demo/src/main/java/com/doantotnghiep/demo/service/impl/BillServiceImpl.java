package com.doantotnghiep.demo.service.impl;

import com.doantotnghiep.demo.dto.request.admin.AddProductRequest;
import com.doantotnghiep.demo.dto.request.user.AddBillRequest;
import com.doantotnghiep.demo.dto.response.admin.BillDetailResponse;
import com.doantotnghiep.demo.dto.response.admin.BillListResponse;
import com.doantotnghiep.demo.dto.response.admin.CouponDetailResponse;
import com.doantotnghiep.demo.dto.response.admin.CouponListResponse;
import com.doantotnghiep.demo.dto.response.user.BillProductDetailResponse;
import com.doantotnghiep.demo.entity.*;
import com.doantotnghiep.demo.mapper.BillMapper;
import com.doantotnghiep.demo.mapper.BillProductMapper;
import com.doantotnghiep.demo.repository.BillRepository;
import com.doantotnghiep.demo.repository.UserRepository;
import com.doantotnghiep.demo.service.BillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {
    private final BillRepository billRepository;
    private final UserRepository userRepository;
    private final BillMapper billMapper;
    private final BillProductMapper billProductMapper;

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addBill(AddBillRequest addBillRequest){
        User user = userRepository.getOne(addBillRequest.getUserId());

        String couponName;
        if(addBillRequest.getDiscountPercent() != 0){
            couponName = addBillRequest.getCouponName();
        }else {
            couponName = "Không có";
        }

        Bill bill = Bill.builder()
                .status(addBillRequest.getStatus())
                .buyDate(new Timestamp(System.currentTimeMillis()))
                .discountPercent(addBillRequest.getDiscountPercent())
                .priceTotal(addBillRequest.getPriceTotal())
                .couponName(couponName)
                .user(user)
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .updatedAt(new Timestamp(System.currentTimeMillis()))
                .isDeleted(false).build();

        billRepository.save(bill);

    }

    @Override
    public BillListResponse getListBill(String userName, Integer sortBy, Pageable pageable){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Bill> cq = cb.createQuery(Bill.class);
        Root<Bill> root = cq.from(Bill.class);
        List<Predicate> listPredicate = new ArrayList<>();

        if (userName != null) {
            listPredicate.add(cb.like(cb.lower(root.get("user").get("name")), "%" + userName.toLowerCase() + "%"));
        }

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
        Long count = em.createQuery(countQuery.select(cb.count(countRoot)).where(cb.and(finalPredicate))).getSingleResult();

        List<BillDetailResponse> responseDTOS = new ArrayList<>();
        query.getResultList().forEach(bill -> responseDTOS.add(billMapper.toListDTO(bill)));

        BillListResponse couponListResponse = new BillListResponse();
        couponListResponse.setList(responseDTOS);
        couponListResponse.setTotal(count);

        return couponListResponse;

    }

    @Override
    public com.doantotnghiep.demo.dto.response.user.BillDetailResponse getBillDetail(Long billId, Integer sortBy, Pageable pageable){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BillProduct> cq = cb.createQuery(BillProduct.class);
        Root<BillProduct> root = cq.from(BillProduct.class);
        List<Predicate> listPredicate = new ArrayList<>();

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

        TypedQuery<BillProduct> query = em.createQuery(cq.select(root).where(cb.and(finalPredicate)).orderBy(order));
        query.setMaxResults(pageable.getPageSize());
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<BillProduct> countRoot = countQuery.from(BillProduct.class);
        Long count = em.createQuery(countQuery.select(cb.count(countRoot)).where(cb.and(finalPredicate))).getSingleResult();

        List<BillProductDetailResponse> responseDTOS = new ArrayList<>();
        query.getResultList().forEach(bill -> responseDTOS.add(billProductMapper.toListDTO(bill)));

        com.doantotnghiep.demo.dto.response.user.BillDetailResponse billDetailResponse = new com.doantotnghiep.demo.dto.response.user.BillDetailResponse();

        Bill bill = billRepository.getOne(billId);

        billDetailResponse.setId(bill.getId());
        billDetailResponse.setStatus(bill.getStatus());
        billDetailResponse.setBuyDate(bill.getBuyDate().getTime());
        billDetailResponse.setDiscountPercent(billDetailResponse.getDiscountPercent());
        billDetailResponse.setPriceTotal(bill.getPriceTotal());
        billDetailResponse.setCouponName(bill.getCouponName());
        billDetailResponse.setUserName(bill.getUser().getName());
        billDetailResponse.setList(responseDTOS);
        billDetailResponse.setTotal(count);

        return billDetailResponse;

    }

    @Override
    public void deleteBill(Long billId){
        Bill coupon = billRepository.getOne(billId);
        if(coupon == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Không tìm thấy bill theo id truyền vào");
        }

        billRepository.deleteById(billId);
    }

    @Override
    public BillDetailResponse billDetail(Long billId){
        Bill bill = billRepository.getOne(billId);

        return billMapper.toListDTO(bill);
    }


}
