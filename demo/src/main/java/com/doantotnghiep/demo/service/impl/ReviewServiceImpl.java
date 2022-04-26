package com.doantotnghiep.demo.service.impl;

import com.doantotnghiep.demo.dto.request.user.AddReviewRequest;
import com.doantotnghiep.demo.dto.response.admin.CouponDetailResponse;
import com.doantotnghiep.demo.dto.response.admin.CouponListResponse;
import com.doantotnghiep.demo.dto.response.admin.ReviewDetailResponse;
import com.doantotnghiep.demo.dto.response.admin.ReviewListResponse;
import com.doantotnghiep.demo.entity.Coupon;
import com.doantotnghiep.demo.entity.Product;
import com.doantotnghiep.demo.entity.Review;
import com.doantotnghiep.demo.entity.User;
import com.doantotnghiep.demo.mapper.ReviewMapper;
import com.doantotnghiep.demo.repository.ProductRepository;
import com.doantotnghiep.demo.repository.ReviewRepository;
import com.doantotnghiep.demo.repository.UserRepository;
import com.doantotnghiep.demo.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class ReviewServiceImpl implements ReviewService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addReview(AddReviewRequest addReviewRequest){
        User user = userRepository.getOne(addReviewRequest.getUserId());
        if(user == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Không tìm thấy với user id truyền vào");
        }

        Product product = productRepository.getOne(addReviewRequest.getProductId());
        if(product == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Không tìm thấy với product id truyền vào");
        }

        Review review = Review.builder()
                .user(user)
                .comment(addReviewRequest.getComment())
                .star(addReviewRequest.getStar())
                .product(product)
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .updatedAt(new Timestamp(System.currentTimeMillis()))
                .isDeleted(false)
                .build();

        reviewRepository.save(review);

        product.setTotalRating(product.getTotalRating() + 1);
        product.setTotalStar(product.getTotalStar() + addReviewRequest.getStar());
        productRepository.save(product);

    }

    //Xem list review cho user
    @Override
    public ReviewListResponse getListReview(Long productId, Pageable pageable){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Review> cq = cb.createQuery(Review.class);
        Root<Review> root = cq.from(Review.class);
        List<Predicate> listPredicate = new ArrayList<>();

        Product product = productRepository.getOne(productId);
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Không tìm thấy với product id truyền vào");
        }

        listPredicate.add(cb.equal((root.get("product").get("id")), productId));

        Path<Object> sort = null;
        Order order = null;

        sort = root.get("createdAt");
        order = cb.desc(sort);


        Predicate[] finalPredicate = new Predicate[listPredicate.size()];
        listPredicate.toArray(finalPredicate);

        TypedQuery<Review> query = em.createQuery(cq.select(root).where(cb.and(finalPredicate)).orderBy(order));
        query.setMaxResults(pageable.getPageSize());
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Review> countRoot = countQuery.from(Review.class);
        Long count = em.createQuery(countQuery.select(cb.count(countRoot)).where(cb.and(finalPredicate))).getSingleResult();

        List<ReviewDetailResponse> responseDTOS = new ArrayList<>();
        query.getResultList().forEach(coupon -> responseDTOS.add(reviewMapper.toListDTO(coupon)));

        ReviewListResponse reviewListResponse = new ReviewListResponse();
        reviewListResponse.setList(responseDTOS);
        reviewListResponse.setTotal(count);

        return reviewListResponse;
    }

    @Override
    public void deleteReview(Long id){
        Review review = reviewRepository.getOne(id);
        if(review == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Không tìm thấy với review id truyền vào");
        }

        reviewRepository.deleteById(id);

    }


}
