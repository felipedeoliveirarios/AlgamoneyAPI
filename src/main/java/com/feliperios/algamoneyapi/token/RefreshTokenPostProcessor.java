package com.feliperios.algamoneyapi.token;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class RefreshTokenPostProcessor implements ResponseBodyAdvice<OAuth2AccessToken> {

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return returnType.getMethod().getName().equals("postAccessToken");
	}

	@Override
	public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

		HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
		HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();

		DefaultOAuth2AccessToken defaultOAuth2AccessToken = (DefaultOAuth2AccessToken) body;

		String refreshTokenValue = body.getRefreshToken().getValue();
		createCookieForRefreshToken(refreshTokenValue, servletRequest, servletResponse);
		removeRefreshTokenFromBody(defaultOAuth2AccessToken);

		return body;
	}

	private void removeRefreshTokenFromBody(DefaultOAuth2AccessToken defaultOAuth2AccessToken) {
		defaultOAuth2AccessToken.setRefreshToken(null);
	}

	private void createCookieForRefreshToken(String refreshTokenValue, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		Cookie refreshTokenCookie = new Cookie("refreshToken", refreshTokenValue);

		refreshTokenCookie.setHttpOnly(true);
		refreshTokenCookie.setSecure(false); // TODO: get value from configuration file
		refreshTokenCookie.setPath(servletRequest.getContextPath() + "/oauth/token");
		refreshTokenCookie.setMaxAge(3600 * 12); // TODO: get value from config file

		servletResponse.addCookie(refreshTokenCookie);
	}
}
