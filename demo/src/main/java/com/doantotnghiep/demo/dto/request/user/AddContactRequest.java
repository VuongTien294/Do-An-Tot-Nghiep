package com.doantotnghiep.demo.dto.request.user;

import lombok.Data;

@Data
public class AddContactRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String subject;
    private String message;
}
