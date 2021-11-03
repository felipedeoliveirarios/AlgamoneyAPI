package com.feliperios.algamoneyapi.resource;

import com.feliperios.algamoneyapi.config.property.AlgamoneyApiProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/tokens")
public class TokenResource {

	@Autowired
	private AlgamoneyApiProperty apiProperty;

	@DeleteMapping("/revoke")
	public void removeTokenFromCookies(HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = new Cookie("refreshToken", null);
		cookie.setHttpOnly(apiProperty.getSecurityOptions().isEnableHTTPS());
		cookie.setSecure(false);
		cookie.setPath(request.getContextPath() + "/oauth/token");
		cookie.setMaxAge(0);

		response.addCookie(cookie);
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}
}
