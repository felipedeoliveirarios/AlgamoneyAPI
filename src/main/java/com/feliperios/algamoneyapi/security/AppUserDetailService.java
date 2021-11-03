package com.feliperios.algamoneyapi.security;

import com.feliperios.algamoneyapi.model.User;
import com.feliperios.algamoneyapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AppUserDetailService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("Nome de usuário e/ou senha inválidos."));

		return new SystemUser(user, getPermissions(user));
	}

	private Collection<? extends GrantedAuthority> getPermissions(User user) {
		Set<SimpleGrantedAuthority> authoritySet = new HashSet<>();
		user.getPermissions().forEach(
				permission -> authoritySet.add(
						new SimpleGrantedAuthority(permission.getDescription().toUpperCase())
				)
		);
		return authoritySet;
	}
}
