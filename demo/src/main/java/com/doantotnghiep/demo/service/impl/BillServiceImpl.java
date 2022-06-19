package com.doantotnghiep.demo.service.impl;

import com.doantotnghiep.demo.dto.request.admin.ChangeBillStatus;
import com.doantotnghiep.demo.dto.request.admin.UpdateShipper;
import com.doantotnghiep.demo.dto.request.user.AddBillRequest;
import com.doantotnghiep.demo.dto.request.user.BuyRequest;
import com.doantotnghiep.demo.dto.request.user.BuyRequest2;
import com.doantotnghiep.demo.dto.response.admin.AdminBillDetailResponse;
import com.doantotnghiep.demo.dto.response.admin.BillListResponse;
import com.doantotnghiep.demo.dto.response.admin.DashBoardBodyResponse;
import com.doantotnghiep.demo.dto.response.pdf.BillProductInfo;
import com.doantotnghiep.demo.dto.response.user.BillProductDetailResponse;
import com.doantotnghiep.demo.dto.response.user.MemberBillDetailResponse;
import com.doantotnghiep.demo.entity.*;
import com.doantotnghiep.demo.mapper.BillMapper;
import com.doantotnghiep.demo.mapper.BillProductMapper;
import com.doantotnghiep.demo.repository.*;
import com.doantotnghiep.demo.service.BillService;
import com.doantotnghiep.demo.service.MailService;
import com.doantotnghiep.demo.service.PdfGenerateService;
import com.doantotnghiep.demo.ultil.BillStatusEnum;
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
import java.util.*;

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
    private final SizeRepository sizeRepository;

    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final PdfGenerateService pdfGenerateService;

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
//        User user = userRepository.getOne(buyRequest.getUserId());
//
//        Long priceTotal = Long.valueOf(0);
//        for(int i= 0 ;i< buyRequest.getListBillProducts().size();i++){
//            Product product = productRepository.getOne(buyRequest.getListBillProducts().get(i).getProductId());
//
//            priceTotal = priceTotal + product.getPrice() * Long.valueOf(buyRequest.getListBillProducts().get(i).getQuantity());
//        }
//
//        String couponName;
//        if(buyRequest.getDiscountPersent() != 0){
//            couponName = buyRequest.getCouponName();
//            priceTotal = priceTotal - Long.valueOf(priceTotal * buyRequest.getDiscountPersent()) / Long.valueOf(100);
//        }else {
//            couponName = "Nonce Coupon";
//        }
//
//        Bill bill = Bill.builder()
//                .status(BillStatusEnum.CHUA_XU_LY.getCode())
//                .buyDate(new Timestamp(System.currentTimeMillis()))
//                .discountPercent(buyRequest.getDiscountPersent())
//                .priceTotal(priceTotal)
//                .couponName(couponName)
//                .user(user)
//                .createdAt(new Timestamp(System.currentTimeMillis()))
//                .updatedAt(new Timestamp(System.currentTimeMillis()))
//                .isDeleted(false).build();
//        billRepository.save(bill);
//
//        for(int i= 0 ;i< buyRequest.getListBillProducts().size();i++){
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTimeInMillis(System.currentTimeMillis());
//
//
//            Product product = productRepository.getOne(buyRequest.getListBillProducts().get(i).getProductId());
//            product.setTotalQuantity(product.getTotalQuantity() - buyRequest.getListBillProducts().get(i).getQuantity());
//            product.setSoldQuantity(product.getSoldQuantity() + buyRequest.getListBillProducts().get(i).getQuantity());
//            productRepository.save(product);
//
//            Size size = sizeRepository.getOne(buyRequest.getListBillProducts().get(i).getSizeId());
//            size.setQuantity(size.getQuantity() - buyRequest.getListBillProducts().get(i).getQuantity());
//            sizeRepository.save(size);
//
//            BillProduct billProduct = BillProduct.builder()
//                    .product(product)
//                    .bill(bill)
//                    .unitPrice(product.getPrice())
//                    .quantity(buyRequest.getListBillProducts().get(i).getQuantity())
//                    .month(calendar.get(Calendar.MONTH) + 1)
//                    .year(calendar.get(Calendar.YEAR))
//                    .createdAt(new Timestamp(System.currentTimeMillis()))
//                    .updatedAt(new Timestamp(System.currentTimeMillis()))
//                    .isDeleted(false)
//                    .build();
//
//            billProductRepository.save(billProduct);
//        }


    }

    @Override
    public void buyProduct2(BuyRequest2 buyRequest){

        User user;

        //Nếu tìm thấy user theo id thì set thẳng user
        if(buyRequest.getUserId() != null){
            user = userRepository.getOne(buyRequest.getUserId());
            user.setName(buyRequest.getName());
            user.setAddress(buyRequest.getAddress());
            user.setEmail(buyRequest.getEmail());
            user.setPhone(buyRequest.getPhone());
            userRepository.save(user);

            log.info("Tim thay user theo user id");
        }
        else {
            //Nếu ko tìm thấy thì tìm theo phone
            User userBuyPhone = userRepository.findUserByphone(buyRequest.getPhone());

            //Nếu ko thấy thì tạo mới bằng phone người dùng nhập
            if(Objects.isNull(userBuyPhone)){
                String userName = UUID.randomUUID().toString();

                List<String> listRoles = Arrays.asList("ROLE_MEMBER");

                user = userRepository.save(User.builder()
                        .name(buyRequest.getName())
                        .roles(listRoles)
                        .username(userName)
                        .password(passwordEncoder.encode("1"))
                        .address(buyRequest.getAddress())
                        .age(buyRequest.getAge())
                        .email(buyRequest.getEmail())
                        .gender(buyRequest.getGender())
                        .phone(buyRequest.getPhone())
                        .enabled(true)
                        .createdAt(new Timestamp(System.currentTimeMillis()))
                        .updatedAt(new Timestamp(System.currentTimeMillis()))
                        .isDeleted(false).build());

                System.out.println("TH ko tìm thấy phone");
                log.info("Khong tim thay phone");
            }else {

                //Nếu tìm thấy thì thay đổi 1 vài trường sau đó save
                userBuyPhone.setAddress(buyRequest.getAddress());
                userBuyPhone.setEmail(buyRequest.getEmail());
                userBuyPhone.setPhone(buyRequest.getPhone());
                userBuyPhone.setName(buyRequest.getName());
                userRepository.save(userBuyPhone);

                user = userBuyPhone;

                System.out.println("TH tìm thấy phone");
                log.info("Tim thay phone");
            }


        }


        String couponName;
        if(buyRequest.getDiscountPersent() != 0){
            couponName = buyRequest.getCouponName();
        }else {
            couponName = "Nonce Coupon";
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        Bill bill = Bill.builder()
                .status(BillStatusEnum.CHUA_XU_LY.getCode())
                .buyDate(new Timestamp(System.currentTimeMillis()))
                .discountPercent(buyRequest.getDiscountPersent())
                .priceTotal(buyRequest.getPriceTotal())
                .couponName(couponName)
                .user(user)
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .updatedAt(new Timestamp(System.currentTimeMillis()))
                .isDeleted(false)
                .month(calendar.get(Calendar.MONTH) + 1)
                .year(calendar.get(Calendar.YEAR))
                .build();
        billRepository.save(bill);

        for(int i= 0 ;i< buyRequest.getListBillProducts().size();i++){
            Calendar calendarBillProduct = Calendar.getInstance();
            calendarBillProduct.setTimeInMillis(System.currentTimeMillis());

            Product product = productRepository.getOne(buyRequest.getListBillProducts().get(i).getProductId());
            product.setTotalQuantity(product.getTotalQuantity() - buyRequest.getListBillProducts().get(i).getQuantity());
            product.setSoldQuantity(product.getSoldQuantity() + buyRequest.getListBillProducts().get(i).getQuantity());
            productRepository.save(product);

            Size size = sizeRepository.getOne(buyRequest.getListBillProducts().get(i).getSizeId());
            size.setQuantity(size.getQuantity() - buyRequest.getListBillProducts().get(i).getQuantity());
            sizeRepository.save(size);

            BillProduct billProduct = BillProduct.builder()
                    .product(product)
                    .bill(bill)
                    .unitPrice(product.getPrice())
                    .quantity(buyRequest.getListBillProducts().get(i).getQuantity())
                    .month(calendarBillProduct.get(Calendar.MONTH) + 1)
                    .year(calendarBillProduct.get(Calendar.YEAR))
                    .createdAt(new Timestamp(System.currentTimeMillis()))
                    .updatedAt(new Timestamp(System.currentTimeMillis()))
                    .isDeleted(false)
                    .sizeId(size.getId())
                    .sizeName(size.getName())
                    .build();

            billProductRepository.save(billProduct);
        }




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
        billDetailResponse.setDiscountPercent(bill.getDiscountPercent());
        billDetailResponse.setPriceTotal(bill.getPriceTotal());
        billDetailResponse.setCouponName(bill.getCouponName());
        billDetailResponse.setName(bill.getUser().getName());
        billDetailResponse.setShipperName(bill.getShipperName());
        billDetailResponse.setShipperPhone(bill.getShipperPhone());

        billDetailResponse.setList(responseDTOS);
        billDetailResponse.setTotal(count);

        return billDetailResponse;

    }

    @Override
    public void deleteBill(Long billId){
        Bill bill = billRepository.getOne(billId);

        List<BillProduct> list = billProductRepository.getBillProductByBillId(billId);
        billProductRepository.deleteAll(list);

        if(bill == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Không tìm thấy bill theo id truyền vào");
        }

        billRepository.deleteById(billId);
    }

    @Override
    public void changeBillStatus(Long billId, ChangeBillStatus changeBillStatus){
        Bill bill = billRepository.getOne(billId);

        if(changeBillStatus.getBillStatus().equals(BillStatusEnum.DA_HUY.getCode())){
            bill.setStatus(changeBillStatus.getBillStatus());

            List<BillProduct> list = billProductRepository.getBillProductByBillId(billId);

            for (BillProduct element: list) {
                Size size = sizeRepository.getOne(element.getSizeId());

                size.setQuantity(size.getQuantity() + element.getQuantity());

                sizeRepository.save(size);

                Product product = size.getProduct();

                product.setTotalQuantity(product.getTotalQuantity() + element.getQuantity());
                product.setSoldQuantity(product.getSoldQuantity() - element.getQuantity());

                productRepository.save(product);
            }
        }else{
            bill.setStatus(changeBillStatus.getBillStatus());
        }


        if(changeBillStatus.getReasonCancel() != null){
            bill.setReasonCancel(changeBillStatus.getReasonCancel());
        }

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

    @Override
    public void updateShipper(Long billId, UpdateShipper updateShipper){
        Bill bill = billRepository.getOne(billId);
        bill.setShipperName(updateShipper.getShipperName());
        bill.setShipperPhone(updateShipper.getShipperPhone());

        billRepository.save(bill);

    }

    @Override
    public BillListResponse getListBillByPhone(String phone, Integer sortBy, Pageable pageable){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Bill> cq = cb.createQuery(Bill.class);
        Root<Bill> root = cq.from(Bill.class);
        List<Predicate> listPredicate = new ArrayList<>();

        listPredicate.add(cb.equal((root.get("user").get("phone")), phone));

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
    public void genPDF(Long billId){
        Map<String, Object> data = new HashMap<>();

        Bill bill = billRepository.getOne(billId);
        data.put("bill",bill);

        User user = bill.getUser();
        data.put("user",user);

        List<BillProduct> list = billProductRepository.getBillProductByBillId(billId);

        List<BillProductInfo> list1 = new ArrayList<>();
        for(BillProduct element : list){
            list1.add(billProductMapper.toBillProductInfo(element));
        }
        data.put("list",list1);

        pdfGenerateService.generatePdfFile("BillGen", data, bill.getUser().getName()+bill.getId()+".pdf");
    }



}
