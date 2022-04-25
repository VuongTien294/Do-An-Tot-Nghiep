package com.doantotnghiep.demo.service.impl;

import com.doantotnghiep.demo.dto.request.user.AddContactRequest;
import com.doantotnghiep.demo.dto.response.admin.AdminContactDetailResponse;
import com.doantotnghiep.demo.dto.response.admin.GetArrayResponse;
import com.doantotnghiep.demo.dto.response.admin.ReviewDetailResponse;
import com.doantotnghiep.demo.dto.response.admin.ReviewListResponse;
import com.doantotnghiep.demo.entity.Contact;
import com.doantotnghiep.demo.entity.Product;
import com.doantotnghiep.demo.entity.Review;
import com.doantotnghiep.demo.repository.ContactRepository;
import com.doantotnghiep.demo.service.ContactService;
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
public class ContactServiceImpl implements ContactService {
    @PersistenceContext
    private EntityManager em;

    private final ContactRepository contactRepository;

    @Override
    public GetArrayResponse<Contact> getListContact(Pageable pageable){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Contact> cq = cb.createQuery(Contact.class);
        Root<Contact> root = cq.from(Contact.class);
        List<Predicate> listPredicate = new ArrayList<>();

        Path<Object> sort = null;
        Order order = null;

        sort = root.get("createdAt");
        order = cb.desc(sort);


        Predicate[] finalPredicate = new Predicate[listPredicate.size()];
        listPredicate.toArray(finalPredicate);

        TypedQuery<Contact> query = em.createQuery(cq.select(root).where(cb.and(finalPredicate)).orderBy(order));
        query.setMaxResults(pageable.getPageSize());
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Contact> countRoot = countQuery.from(Contact.class);
        Long count = em.createQuery(countQuery.select(cb.count(countRoot)).where(cb.and(finalPredicate))).getSingleResult();

        List<Contact> responseDTOS = new ArrayList<>();
        query.getResultList().forEach(contact -> responseDTOS.add(contact));

        GetArrayResponse<Contact> listResponse = new GetArrayResponse();
        listResponse.setRows(responseDTOS);
        listResponse.setTotal(count);

        return listResponse;
    }

    @Override
    public void addContact(AddContactRequest request){
         Contact contact = Contact.builder()
                 .firstName(request.getFirstName())
                 .lastName(request.getLastName())
                 .response(false)
                 .createdAt(new Timestamp(System.currentTimeMillis()))
                 .updatedAt(new Timestamp(System.currentTimeMillis()))
                 .isDeleted(false)
                 .email(request.getEmail())
                 .message(request.getMessage())
                 .subject(request.getSubject())
                 .build();

         contactRepository.save(contact);

    }

    @Override
    public void deleteContact(Long id){
        contactRepository.deleteById(id);
    }
}
