package com.doantotnghiep.demo.controller.admin;

import com.doantotnghiep.demo.dto.request.admin.ContactRequest;
import com.doantotnghiep.demo.entity.Contact;
import com.doantotnghiep.demo.repository.ContactRepository;
import com.doantotnghiep.demo.service.MailService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = -1)
@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class AdminContactController {
    private final MailService mailService;
    private final ContactRepository contactRepository;

    @ApiOperation("Api cho admin trả lời thư liên hệ")
    @PostMapping("/admin/contact/response/{contactId}")
    public void sendMail(
            @PathVariable(required = true) Long contactId,
            @RequestBody(required = true) ContactRequest request
    ){
        Contact contact = contactRepository.getOne(contactId);
        mailService.sendMail(contact.getLastName(), request.getMessage(), contact.getEmail());
    }
}
