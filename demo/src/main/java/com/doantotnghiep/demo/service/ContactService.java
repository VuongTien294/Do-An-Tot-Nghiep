package com.doantotnghiep.demo.service;

import com.doantotnghiep.demo.dto.response.admin.GetArrayResponse;
import com.doantotnghiep.demo.entity.Contact;
import org.springframework.data.domain.Pageable;

public interface ContactService {
    GetArrayResponse<Contact> getListContact(Pageable pageable);
}
