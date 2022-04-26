package com.doantotnghiep.demo.controller.member;

import com.doantotnghiep.demo.dto.request.user.AddContactRequest;
import com.doantotnghiep.demo.service.ContactService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = -1)
@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class MemberContactController {
    private final ContactService contactService;

    @ApiOperation("Api cho member gửi liên hệ")
    @PostMapping("/contact/add")
    public void addContact(
            @RequestBody(required = true) AddContactRequest addContactRequest
    ) {
        contactService.addContact(addContactRequest);
    }
}
