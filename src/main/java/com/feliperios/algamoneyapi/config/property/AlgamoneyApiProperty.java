package com.feliperios.algamoneyapi.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("algamoney")
public class AlgamoneyApiProperty {

	private final Security securityOptions = new Security();

	public Security getSecurityOptions() {
		return securityOptions;
	}

	public static class Security {
		private boolean enableHTTPS;
		private String allowedOrigin = "http://localhost:8000";

		public String getAllowedOrigin() {
			return allowedOrigin;
		}

		public void setAllowedOrigin(String allowedOrigin) {
			this.allowedOrigin = allowedOrigin;
		}

		public boolean isEnableHTTPS() {
			return enableHTTPS;
		}

		public void setEnableHTTPS(boolean enableHTTPS) {
			this.enableHTTPS = enableHTTPS;
		}
	}
}
