package com.feliperios.algamoneyapi.security;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

public class CustomTokenEnchancer implements TokenEnhancer {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
		SystemUser systemUser = (SystemUser) oAuth2Authentication.getPrincipal();

		Map<String, Object> extraInfo = new HashMap<>();
		extraInfo.put("nome", systemUser.getUser().getName());

		((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(extraInfo);
		return oAuth2AccessToken;
	}

}
