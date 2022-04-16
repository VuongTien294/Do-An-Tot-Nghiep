package com.doantotnghiep.demo.dto.response.user;

import org.springframework.security.core.userdetails.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

@Getter
@Setter
public class UserPrincipal extends User implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private List<String> roles;


    public UserPrincipal(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
}
