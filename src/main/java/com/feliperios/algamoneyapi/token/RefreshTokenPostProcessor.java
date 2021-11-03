package com.feliperios.algamoneyapi.token;

import com.feliperios.algamoneyapi.config.AuthorizationServerConfig;
import com.feliperios.algamoneyapi.config.property.AlgamoneyApiProperty;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	AuthorizationServerConfig authorizationServerConfig;

	@Autowired
	AlgamoneyApiProperty apiProperty;

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return returnType.getMethod().getName().equals("postAccessToken");
	}

	@Override
	public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

		HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
		HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
		DefaultOAuth2AccessToken defaultOAuth2AccessToken = (DefaultOAuth2AccessToken) body;

		refreshTokenToCookie(defaultOAuth2AccessToken, servletRequest, servletResponse);

		return body;
	}

	// Move o Refresh Token do token de acesso do OAuth2 para um cookie.
	private void refreshTokenToCookie(DefaultOAuth2AccessToken token, HttpServletRequest request, HttpServletResponse response) {
		// Recupera o campo refresh_token do token de acesso do OAuth2 (JSON)
		String refreshToken = token.getRefreshToken().getValue();
		if (refreshToken == null) System.out.println("Houston...");
		// Cria o cookie e define as propriedades
		Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
		refreshTokenCookie.setHttpOnly(true);
		refreshTokenCookie.setSecure(apiProperty.getSecurityOptions().isEnableHTTPS());
		refreshTokenCookie.setPath(request.getContextPath() + "/oauth/token");
		refreshTokenCookie.setMaxAge(authorizationServerConfig.getRefreshTokenTTL());
		// Adiciona o cookie à resposta
		response.addCookie(refreshTokenCookie);
		// Remove o campo refresh_token da estrutura do token de acesso do OAuth2.
		token.setRefreshToken(null);
	}

}
