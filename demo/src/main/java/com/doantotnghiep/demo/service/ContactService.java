package com.doantotnghiep.demo.service;

import com.doantotnghiep.demo.dto.request.user.AddContactRequest;
import com.doantotnghiep.demo.dto.response.admin.GetArrayResponse;
import com.doantotnghiep.demo.entity.Contact;
import org.springframework.data.domain.Pageable;

public interface ContactService {
    GetArrayResponse<Contact> getListContact(Pageable pageable);

    void addContact(AddContactRequest request);

    void deleteContact(Long id);
}
