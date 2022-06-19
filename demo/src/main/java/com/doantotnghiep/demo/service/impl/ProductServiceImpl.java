package com.doantotnghiep.demo.service.impl;

import com.doantotnghiep.demo.dto.request.admin.AddProductRequest;
import com.doantotnghiep.demo.dto.request.admin.ModifiedProductRequest;
import com.doantotnghiep.demo.dto.response.admin.ProductDetailResponse;
import com.doantotnghiep.demo.dto.response.admin.ProductListResponse;
import com.doantotnghiep.demo.dto.response.admin.SizeDetailResponse;
import com.doantotnghiep.demo.entity.Product;
import com.doantotnghiep.demo.entity.Size;
import com.doantotnghiep.demo.mapper.ProductMapper;
import com.doantotnghiep.demo.mapper.SizeMapper;
import com.doantotnghiep.demo.repository.ProductRepository;
import com.doantotnghiep.demo.repository.SizeRepository;
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
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final SizeRepository sizeRepository;
    private final SizeMapper sizeMapper;

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addProduct(AddProductRequest addProductRequest){

        Integer totalQuantity = 0;
        for(int i = 0 ; i< addProductRequest.getListSize().size();i++){
            totalQuantity = totalQuantity + addProductRequest.getListSize().get(i).getQuantity();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        Product product = Product.builder()
                .name(addProductRequest.getName())
                .price(addProductRequest.getPrice())
                .totalQuantity(totalQuantity)
                .soldQuantity(0)
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
                .totalRating(0)
                .totalStar(Long.valueOf(0))
                .isDeleted(false).build();

        productRepository.save(product);

        for(int i = 0 ; i< addProductRequest.getListSize().size();i++){

            Size size = Size.builder()
                    .name(addProductRequest.getListSize().get(i).getName())
                    .product(product)
                    .quantity(addProductRequest.getListSize().get(i).getQuantity())
                    .createdAt(new Timestamp(System.currentTimeMillis()))
                    .updatedAt(new Timestamp(System.currentTimeMillis()))
                    .isDeleted(false)
                    .build();

            sizeRepository.save(size);
        }

    }


    @Override
    public void modifiedProduct(Long id, ModifiedProductRequest modifiedProductRequest){

        Product product = productRepository.getOne(id);
        if(product == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Không tìm thấy product theo id truyền vào");
        }

        product.setId(product.getId());

        if(modifiedProductRequest.getName() != null){
            product.setName(modifiedProductRequest.getName());
        }

        if(modifiedProductRequest.getPrice() != null){
            product.setPrice(modifiedProductRequest.getPrice());
        }

        if(modifiedProductRequest.getDescription() != null){
            product.setDescription(modifiedProductRequest.getDescription());
        }

        if(modifiedProductRequest.getManufacturer() != null){
            product.setManufacturer(modifiedProductRequest.getManufacturer());
        }

        if(modifiedProductRequest.getImage() != null){
            product.setImage(modifiedProductRequest.getImage());
        }

        //

        if(modifiedProductRequest.getGender() != null){
            product.setGender(modifiedProductRequest.getGender());
        }

        if(modifiedProductRequest.getBranch() != null){
            product.setBranch(modifiedProductRequest.getBranch());
        }

        if(modifiedProductRequest.getStyle() != null){
            product.setStyle(modifiedProductRequest.getStyle());
        }

        if(modifiedProductRequest.getColor() != null){
            product.setColor(modifiedProductRequest.getColor());
        }

        if(modifiedProductRequest.getMaterial() != null){
            product.setMaterial(modifiedProductRequest.getMaterial());
        }

        if(modifiedProductRequest.getTechnology() != null){
            product.setTechnology(modifiedProductRequest.getTechnology());
        }

        product.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        productRepository.save(product);

//        for(int i = 0 ; i< modifiedProductRequest.getListSize().size();i++){
//
//            Size size = sizeRepository.getOne(modifiedProductRequest.getListSize().get(i).getId());
//            if(size == null){
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Không tìm thấy size theo id truyền vào");
//            }
//
//            size.setName(modifiedProductRequest.getListSize().get(i).getName());
//            size.setQuantity(modifiedProductRequest.getListSize().get(i).getQuantity());
//            size.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
//
//            sizeRepository.save(size);
//        }

        Integer quantity = 0;
        for(int i = 0 ; i< modifiedProductRequest.getListSize().size();i++){

            if(modifiedProductRequest.getListSize().get(i).getId() != null){
                Size size = sizeRepository.getOne(modifiedProductRequest.getListSize().get(i).getId());
                size.setName(modifiedProductRequest.getListSize().get(i).getName());
                size.setQuantity(modifiedProductRequest.getListSize().get(i).getQuantity());
                size.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

                quantity = quantity + modifiedProductRequest.getListSize().get(i).getQuantity();
                sizeRepository.save(size);
            }else {
                Size size = Size.builder()
                        .name(modifiedProductRequest.getListSize().get(i).getName())
                        .quantity(modifiedProductRequest.getListSize().get(i).getQuantity())
                        .createdAt(new Timestamp(System.currentTimeMillis()))
                        .updatedAt(new Timestamp(System.currentTimeMillis()))
                        .isDeleted(false)
                        .product(product)
                        .build();

                quantity = quantity + modifiedProductRequest.getListSize().get(i).getQuantity();

                sizeRepository.save(size);

            }
        }

        product.setTotalQuantity(quantity);
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
        productDetailResponse.setGender(product.getGender());
        productDetailResponse.setBranch(product.getBranch());
        productDetailResponse.setStyle(product.getStyle());
        productDetailResponse.setColor(product.getColor());
        productDetailResponse.setMaterial(product.getMaterial());
        productDetailResponse.setTechnology(product.getTechnology());

        List<Size> list = sizeRepository.getListSizeByproductId(product.getId());
        List<SizeDetailResponse> response = new ArrayList<>();
        for (Size size: list) {
            response.add(sizeMapper.toListDTO(size));
        }
        productDetailResponse.setSizeDetailResponses(response);


        return productDetailResponse;

    }

    @Override
    public void deleteProduct(Long id){
        Product product = productRepository.getOne(id);
        if(product == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Không tìm thấy user theo id truyền vào");
        }

        List<Size> list = sizeRepository.getListSizeByproductId(id);
        sizeRepository.deleteAll(list);

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

    @Override
    public ProductListResponse getListProductHomeBestSeller(){

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> root = cq.from(Product.class);
        List<Predicate> listPredicate = new ArrayList<>();

        Path<Object> sort = null;
        Order order = null;

        sort = root.get("soldQuantity");
        order = cb.desc(sort);

        Predicate[] finalPredicate = new Predicate[listPredicate.size()];
        listPredicate.toArray(finalPredicate);

        TypedQuery<Product> query = em.createQuery(cq.select(root).where(cb.and(finalPredicate)).orderBy(order));

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

    @Override
    public ProductListResponse getListProductForUser(String productName, Integer branch, Integer gender, Integer style, Integer color, Integer material, Integer tech, Integer sortBy, Pageable pageable){

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> root = cq.from(Product.class);
        List<Predicate> listPredicate = new ArrayList<>();

        if (productName != null) {
            listPredicate.add(cb.like(cb.lower(root.get("name")), "%" + productName.toLowerCase() + "%"));
        }

        if(Objects.nonNull(branch)){
            listPredicate.add(cb.equal((root.get("branch")), branch));
        }

        if(Objects.nonNull(gender)){
            listPredicate.add(cb.equal((root.get("gender")), gender));
        }

        if(Objects.nonNull(style)){
            listPredicate.add(cb.equal((root.get("style")), style));
        }

        if(Objects.nonNull(color)){
            listPredicate.add(cb.equal((root.get("color")), color));
        }

        if(Objects.nonNull(material)){
            listPredicate.add(cb.equal((root.get("material")), material));
        }

        if(Objects.nonNull(tech)){
            listPredicate.add(cb.equal((root.get("technology")), tech));
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
