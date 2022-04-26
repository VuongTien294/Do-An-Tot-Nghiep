package com.doantotnghiep.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "contact")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contact extends BaseModel{
    private String firstName;
    private String lastName;
    private String email;
    private String subject;
    private String message;

    @Column(name = "response",columnDefinition = "boolean default false")
    private boolean response;
}
