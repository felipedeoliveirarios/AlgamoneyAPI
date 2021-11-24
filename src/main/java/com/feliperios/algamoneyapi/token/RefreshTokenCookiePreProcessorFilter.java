package com.feliperios.algamoneyapi.token;

import org.apache.catalina.util.ParameterMap;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Map;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RefreshTokenCookiePreProcessorFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Filter.super.init(filterConfig);
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;

		if (request.getRequestURI().equalsIgnoreCase("/oauth/token")
				&& request.getParameter("grant_type").equals("refresh_token")
				&& request.getCookies() != null) {
			for (Cookie cookie : request.getCookies()) {
				if (cookie.getName().equals("refreshToken")) {
					String refreshTokenValue = cookie.getValue();
					request = new RefreshTokenServletRequestWrapper(request, refreshTokenValue);
					break;
				}
			}
		}
		filterChain.doFilter(request, servletResponse);
	}

	@Override
	public void destroy() {
		Filter.super.destroy();
	}

	static class RefreshTokenServletRequestWrapper extends HttpServletRequestWrapper {

		private String refreshTokenValue;

		public RefreshTokenServletRequestWrapper(HttpServletRequest request, String refreshTokenValue) {
			super(request);
			this.refreshTokenValue = refreshTokenValue;
		}

		@Override
		public Map<String, String[]> getParameterMap() {
			// Equivale ao super.getParameterMap()
			ParameterMap<String, String[]> map = new ParameterMap<>(getRequest().getParameterMap());

			// Adiciona o refreshToken aos parâmetros da requisição
			map.put("refresh_token", new String[]{this.refreshTokenValue});
			map.setLocked(true);

			return map;
		}
	}
}
