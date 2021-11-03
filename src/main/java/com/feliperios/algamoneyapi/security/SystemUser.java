package com.feliperios.algamoneyapi.security;

import com.feliperios.algamoneyapi.model.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class SystemUser extends org.springframework.security.core.userdetails.User {

	private User user;

	public SystemUser(User user, Collection<? extends GrantedAuthority> authorities) {
		super(user.getEmail(), user.getPassword(), authorities);
		this.user = user;
	}

	public User getUser() {
		return user;
	}
}
