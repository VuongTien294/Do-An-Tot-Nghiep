package com.doantotnghiep.demo.service.impl;

import com.doantotnghiep.demo.dto.request.admin.AddProductRequest;
import com.doantotnghiep.demo.dto.response.admin.ProductDetailResponse;
import com.doantotnghiep.demo.dto.response.admin.ProductListResponse;
import com.doantotnghiep.demo.entity.Product;
import com.doantotnghiep.demo.mapper.ProductMapper;
import com.doantotnghiep.demo.repository.ProductRepository;
import com.doantotnghiep.demo.service.ProductService;
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
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addProduct(AddProductRequest addProductRequest){

        Product product = Product.builder()
                .name(addProductRequest.getName())
                .price(addProductRequest.getPrice())
                .description(addProductRequest.getDescription())
                .manufacturer(addProductRequest.getManufacturer())
                .image(addProductRequest.getImage())
                .gender(addProductRequest.getGender())
                .branch(addProductRequest.getBranch())
                .style(addProductRequest.getStyle())
                .color(addProductRequest.getColor())
                .material(addProductRequest.getMaterial())
                .technology(addProductRequest.getTechnology())
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .updatedAt(new Timestamp(System.currentTimeMillis()))
                .isDeleted(false).build();

        productRepository.save(product);

    }

    @Override
    public void modifiedProduct(AddProductRequest addProductRequest){

        Product product = productRepository.getOne(addProductRequest.getId());
        if(product == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Không tìm thấy user theo id truyền vào");
        }

        product.setId(product.getId());

        if(addProductRequest.getName() != null){
            product.setName(addProductRequest.getName());
        }

        if(addProductRequest.getPrice() != null){
            product.setPrice(addProductRequest.getPrice());
        }

        if(addProductRequest.getDescription() != null){
            product.setDescription(addProductRequest.getDescription());
        }

        if(addProductRequest.getManufacturer() != null){
            product.setManufacturer(addProductRequest.getManufacturer());
        }

        if(addProductRequest.getImage() != null){
            product.setImage(addProductRequest.getImage());
        }

        product.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        productRepository.save(product);

    }

    @Override
    public ProductDetailResponse getProductDetail(Long id){

        Product product = productRepository.getOne(id);
        if(product == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Không tìm thấy user theo id truyền vào");
        }

        ProductDetailResponse productDetailResponse = new ProductDetailResponse();
        productDetailResponse.setId(product.getId());
        productDetailResponse.setName(product.getName());
        productDetailResponse.setPrice(product.getPrice());
        productDetailResponse.setTotalQuantity(product.getTotalQuantity());
        productDetailResponse.setSoldQuantity(product.getSoldQuantity());
        productDetailResponse.setDescription(product.getDescription());
        productDetailResponse.setManufacturer(product.getManufacturer());
        productDetailResponse.setTotalRating(product.getTotalRating());
        productDetailResponse.setTotalStar(product.getTotalStar());
        productDetailResponse.setImage(product.getImage());

        return productDetailResponse;

    }

    @Override
    public void deleteProduct(Long id){

        Product product = productRepository.getOne(id);
        if(product == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Không tìm thấy user theo id truyền vào");
        }

        productRepository.deleteById(id);


    }

    @Override
    public ProductListResponse getListProduct(String productName, Integer sortBy, Pageable pageable){

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> root = cq.from(Product.class);
        List<Predicate> listPredicate = new ArrayList<>();

        if (productName != null) {
            listPredicate.add(cb.like(cb.lower(root.get("name")), "%" + productName.toLowerCase() + "%"));
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

        TypedQuery<Product> query = em.createQuery(cq.select(root).where(cb.and(finalPredicate)).orderBy(order));
        query.setMaxResults(pageable.getPageSize());
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Product> countRoot = countQuery.from(Product.class);
        Long count = em.createQuery(countQuery.select(cb.count(countRoot)).where(cb.and(finalPredicate))).getSingleResult();

        List<ProductDetailResponse> responseDTOS = new ArrayList<>();
        query.getResultList().forEach(product -> responseDTOS.add(productMapper.toListDTO(product)));

        ProductListResponse productListResponse = new ProductListResponse();
        productListResponse.setList(responseDTOS);
        productListResponse.setTotal(count);

        return productListResponse;
    }


}
