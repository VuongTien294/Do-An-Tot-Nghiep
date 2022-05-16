package com.doantotnghiep.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import javax.persistence.*;
import java.util.List;

@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseModel{
    @Column(name = "name")
    private String name;

    @Column(name = "user_name",unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "address")
    private String address;

    @Column(name = "age")
    private Integer age;

    @Column(name = "email")
    private String email;

    @Column(name = "gender")
    private String gender;

    @Column(name = "phone")
    private String phone;

    @ElementCollection
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), uniqueConstraints = {
            @UniqueConstraint(columnNames = { "user_id", "role" }) })
    @Column(name = "role")
    private List<String> roles;

    @Column(name = "enabled",columnDefinition = "boolean default true")
    private Boolean enabled;

    public User(Long id) {
        super();
        this.id = id;
    }




}
