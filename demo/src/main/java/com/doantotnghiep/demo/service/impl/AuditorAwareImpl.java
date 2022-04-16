package com.doantotnghiep.demo.service.impl;

import java.util.Optional;

import com.doantotnghiep.demo.dto.response.user.UserPrincipal;
import com.doantotnghiep.demo.entity.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;


public class AuditorAwareImpl implements AuditorAware<User> {
	@Override
	public Optional<User> getCurrentAuditor() {
		if (SecurityContextHolder.getContext().getAuthentication() != null
				&& !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
			UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			return Optional.of(new User(currentUser.getId()));
		}
		return Optional.ofNullable(null);
	}

}
