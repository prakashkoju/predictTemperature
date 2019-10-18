package com.example.weather;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;

@ConfigurationProperties("app.weather")
public class ApiProperties {

	private final Api api = new Api();

	public Api getApi() {
		return this.api;
	}

	public static class Api {

		@NotNull
		private String key;

		@NotNull
		private String url;

		@NotNull
		private String authid;

		@NotNull
		private String tokenid;

		@NotNull
		private String locurl;

		public String getKey() {
			return this.key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getUrl() {
			return this.url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getAuthid() {
			return authid;
		}

		public void setAuthid(String authid) {
			this.authid = authid;
		}

		public String getTokenid() {
			return tokenid;
		}

		public void setTokenid(String tokenid) {
			this.tokenid = tokenid;
		}

		public String getLocurl() {
			return locurl;
		}

		public void setLocurl(String locurl) {
			this.locurl = locurl;
		}
	}

}
