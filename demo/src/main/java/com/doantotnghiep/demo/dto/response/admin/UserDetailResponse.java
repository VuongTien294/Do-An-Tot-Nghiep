package com.doantotnghiep.demo.dto.response.admin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDetailResponse implements Serializable {
    private Long id;

    private String name;

    @JsonProperty("user_name")
    private String userName;

    private String address;

    @JsonProperty("age")
    private Integer age;

    @JsonProperty("email")
    private String email;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("roles")
    private List<String> roles;

    @JsonProperty("enabled")
    private Boolean enabled;
}
