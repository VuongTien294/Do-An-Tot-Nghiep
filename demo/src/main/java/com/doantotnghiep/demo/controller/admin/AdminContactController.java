package com.doantotnghiep.demo.controller.admin;

import com.doantotnghiep.demo.dto.request.admin.ContactRequest;
import com.doantotnghiep.demo.dto.response.admin.GetArrayResponse;
import com.doantotnghiep.demo.entity.Contact;
import com.doantotnghiep.demo.repository.ContactRepository;
import com.doantotnghiep.demo.service.ContactService;
import com.doantotnghiep.demo.service.MailService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@CrossOrigin(origins = "*", maxAge = -1)
@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class AdminContactController {
    private final MailService mailService;
    private final ContactRepository contactRepository;
    private final ContactService contactService;

    @ApiOperation("Api cho admin trả lời thư liên hệ")
    @PostMapping("/admin/contact/response/{contactId}")
    public void sendMail(
            @PathVariable(required = true) Long contactId,
            @RequestBody(required = true) ContactRequest request
    ){
        Contact contact = contactRepository.getOne(contactId);
        contact.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        contact.setResponse(true);
        contactRepository.save(contact);

        mailService.sendMail(contact.getLastName(), request.getMessage(), contact.getEmail());
    }

    @ApiOperation("Api cho admin xóa contact")
    @GetMapping("/admin/contact/delete/{contactId}")
    public void sendMail(
            @PathVariable(required = true) Long contactId
    ){
        contactService.deleteContact(contactId);
    }

    @ApiOperation("Api cho admin xem list contact")
    @GetMapping("/admin/contact/list")
    public GetArrayResponse<Contact> sendMail(
            Pageable pageable
    ){
        return contactService.getListContact(pageable);
    }
}
