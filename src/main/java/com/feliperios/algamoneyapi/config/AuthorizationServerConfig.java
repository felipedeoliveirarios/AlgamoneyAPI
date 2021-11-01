package com.feliperios.algamoneyapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	private int tokenTTL = 60;
	private int refreshTokenTTL = 3600;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
				.withClient("angular")
				.secret("{noop}4ngul4r")
				.scopes("read", "write")
				.authorizedGrantTypes("password", "refresh_token")
				.accessTokenValiditySeconds(tokenTTL)
				.refreshTokenValiditySeconds(refreshTokenTTL);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
				.tokenStore(getTokenStore())
				.accessTokenConverter(getAcessTokenConverter())
				.reuseRefreshTokens(false)
				.authenticationManager(authenticationManager);
	}

	@Bean
	public JwtAccessTokenConverter getAcessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		jwtAccessTokenConverter.setSigningKey("algaworks");
		return jwtAccessTokenConverter;
	}

	@Bean
	public TokenStore getTokenStore() {
		return new JwtTokenStore(getAcessTokenConverter());
	}

	public int getTokenTTL() {
		return tokenTTL;
	}

	public void setTokenTTL(int tokenTTL) {
		this.tokenTTL = tokenTTL;
	}

	public int getRefreshTokenTTL() {
		return refreshTokenTTL;
	}

	public void setRefreshTokenTTL(int refreshTokenTTL) {
		this.refreshTokenTTL = refreshTokenTTL;
	}
}
