package com.doantotnghiep.demo.service.impl;

import com.doantotnghiep.demo.dto.request.user.AddBillRequest;
import com.doantotnghiep.demo.dto.request.user.BuyRequest;
import com.doantotnghiep.demo.dto.response.admin.AdminBillDetailResponse;
import com.doantotnghiep.demo.dto.response.admin.BillListResponse;
import com.doantotnghiep.demo.dto.response.user.BillProductDetailResponse;
import com.doantotnghiep.demo.dto.response.user.MemberBillDetailResponse;
import com.doantotnghiep.demo.entity.*;
import com.doantotnghiep.demo.mapper.BillMapper;
import com.doantotnghiep.demo.mapper.BillProductMapper;
import com.doantotnghiep.demo.repository.BillProductRepository;
import com.doantotnghiep.demo.repository.BillRepository;
import com.doantotnghiep.demo.repository.ProductRepository;
import com.doantotnghiep.demo.repository.UserRepository;
import com.doantotnghiep.demo.service.BillService;
import com.doantotnghiep.demo.ultil.BillStatusEnum;
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
import java.util.Calendar;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {
    private final BillRepository billRepository;
    private final UserRepository userRepository;
    private final BillMapper billMapper;
    private final BillProductMapper billProductMapper;
    private final BillProductRepository billProductRepository;
    private final ProductRepository productRepository;

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
    public void buyProduct(BuyRequest buyRequest){
        User user = userRepository.getOne(buyRequest.getUserId());

        Long priceTotal = Long.valueOf(0);
        for(int i= 0 ;i< buyRequest.getListBillProducts().size();i++){
            priceTotal = priceTotal + buyRequest.getListBillProducts().get(i).getUnitPrice() * buyRequest.getListBillProducts().get(i).getQuantity();
        }

        String couponName;
        if(buyRequest.getDiscountPersent() != 0){
            couponName = buyRequest.getCouponName();
            priceTotal = priceTotal / buyRequest.getDiscountPersent();
        }else {
            couponName = "Không có";
        }

        Bill bill = Bill.builder()
                .status(BillStatusEnum.CHUA_XU_LY.getCode())
                .buyDate(new Timestamp(System.currentTimeMillis()))
                .discountPercent(buyRequest.getDiscountPersent())
                .priceTotal(priceTotal)
                .couponName(couponName)
                .user(user)
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .updatedAt(new Timestamp(System.currentTimeMillis()))
                .isDeleted(false).build();

        for(int i= 0 ;i< buyRequest.getListBillProducts().size();i++){
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());


            Product product = productRepository.getOne(buyRequest.getListBillProducts().get(i).getProductId());

            BillProduct billProduct = BillProduct.builder()
                    .product(product)
                    .bill(bill)
                    .unitPrice(buyRequest.getListBillProducts().get(i).getUnitPrice())
                    .quantity(buyRequest.getListBillProducts().get(i).getQuantity())
                    .month(calendar.get(Calendar.MONTH))
                    .year(calendar.get(Calendar.YEAR))
                    .build();

            billProductRepository.save(billProduct);
        }

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

        List<AdminBillDetailResponse> responseDTOS = new ArrayList<>();
        query.getResultList().forEach(bill -> responseDTOS.add(billMapper.toListDTO(bill)));

        BillListResponse couponListResponse = new BillListResponse();
        couponListResponse.setList(responseDTOS);
        couponListResponse.setTotal(count);

        return couponListResponse;

    }

    @Override
    public MemberBillDetailResponse getBillDetail(Long billId, Integer sortBy, Pageable pageable){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BillProduct> cq = cb.createQuery(BillProduct.class);
        Root<BillProduct> root = cq.from(BillProduct.class);
        List<Predicate> listPredicate = new ArrayList<>();

        listPredicate.add(cb.equal((root.get("bill").get("id")), billId));

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

        MemberBillDetailResponse billDetailResponse = new MemberBillDetailResponse();

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
    public void changeBillStatus(Long billId, Integer billStatus){
        Bill bill = billRepository.getOne(billId);
        bill.setStatus(billStatus);
        billRepository.save(bill);

    }

    @Override
    public BillListResponse getListBillForUser(Long userId, Integer sortBy, Pageable pageable){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Bill> cq = cb.createQuery(Bill.class);
        Root<Bill> root = cq.from(Bill.class);
        List<Predicate> listPredicate = new ArrayList<>();

        listPredicate.add(cb.equal((root.get("user").get("id")), userId));

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

        List<AdminBillDetailResponse> responseDTOS = new ArrayList<>();
        query.getResultList().forEach(bill -> responseDTOS.add(billMapper.toListDTO(bill)));

        BillListResponse couponListResponse = new BillListResponse();
        couponListResponse.setList(responseDTOS);
        couponListResponse.setTotal(count);

        return couponListResponse;

    }
}
