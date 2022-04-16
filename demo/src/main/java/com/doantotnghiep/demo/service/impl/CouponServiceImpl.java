package com.doantotnghiep.demo.service.impl;

import com.doantotnghiep.demo.dto.request.admin.AddCouponRequest;
import com.doantotnghiep.demo.dto.response.admin.CouponDetailResponse;
import com.doantotnghiep.demo.dto.response.admin.CouponListResponse;
import com.doantotnghiep.demo.entity.Coupon;
import com.doantotnghiep.demo.mapper.CouponMapper;
import com.doantotnghiep.demo.repository.CouponRepository;
import com.doantotnghiep.demo.service.CouponService;
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
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
    private final CouponRepository couponRepository;
    private final CouponMapper couponMapper;

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addCoupon(AddCouponRequest addCouponRequest){

        Coupon coupon = Coupon.builder()
                .name(addCouponRequest.getName())
                .code(addCouponRequest.getCode())
                .percent(addCouponRequest.getPercent()).build();

        couponRepository.save(coupon);
    }

    @Override
    public void modifiedCoupon(AddCouponRequest addCouponRequest){

        Coupon coupon = couponRepository.getOne(addCouponRequest.getId());
        if(coupon == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Không tìm thấy coupon theo id truyền vào");
        }

        if(!addCouponRequest.getName().equals("")){
            coupon.setName(addCouponRequest.getName());
        }

        if(!addCouponRequest.getCode().equals("")){
            coupon.setCode(addCouponRequest.getCode());
        }

        if(addCouponRequest.getPercent() != null){
            coupon.setPercent(addCouponRequest.getPercent());
        }


        coupon.setId(coupon.getId());
        couponRepository.save(coupon);

    }

    @Override
    public void deleteCoupon(Long couponId){

        Coupon coupon = couponRepository.getOne(couponId);
        if(coupon == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Không tìm thấy coupon theo id truyền vào");
        }

        couponRepository.deleteById(couponId);

    }

    @Override
    public CouponDetailResponse getCouponByCode(String code){

        Coupon coupon = couponRepository.findCouponByCode(code);
        if(coupon == null){
            return null;
        }else {
            CouponDetailResponse couponDetailResponse = CouponDetailResponse.builder()
                    .name(coupon.getName())
                    .code(coupon.getCode())
                    .percent(coupon.getPercent()).build();

            return couponDetailResponse;
        }

    }

    @Override
    public CouponListResponse getListCoupon(String couponName, Integer sortBy, Pageable pageable){

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Coupon> cq = cb.createQuery(Coupon.class);
        Root<Coupon> root = cq.from(Coupon.class);
        List<Predicate> listPredicate = new ArrayList<>();

        if (couponName != null) {
            listPredicate.add(cb.like(cb.lower(root.get("name")), "%" + couponName.toLowerCase() + "%"));
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

        TypedQuery<Coupon> query = em.createQuery(cq.select(root).where(cb.and(finalPredicate)).orderBy(order));
        query.setMaxResults(pageable.getPageSize());
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Coupon> countRoot = countQuery.from(Coupon.class);
        Long count = em.createQuery(countQuery.select(cb.count(countRoot)).where(cb.and(finalPredicate))).getSingleResult();

        List<CouponDetailResponse> responseDTOS = new ArrayList<>();
        query.getResultList().forEach(coupon -> responseDTOS.add(couponMapper.toListDTO(coupon)));

        CouponListResponse couponListResponse = new CouponListResponse();
        couponListResponse.setList(responseDTOS);
        couponListResponse.setTotal(count);

        return couponListResponse;
    }

}
