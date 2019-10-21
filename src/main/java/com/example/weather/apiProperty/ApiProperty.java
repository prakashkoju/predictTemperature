package com.example.weather.apiProperty;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@ConfigurationProperties(prefix = "app.weather")
public class ApiProperty {

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
		private String authId;

		@NotNull
		private String tokenId;

		@NotNull
		private String locationUrl;

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

		public String getAuthId() {
			return authId;
		}

		public void setAuthId(String authId) {
			this.authId = authId;
		}

		public String getTokenId() {
			return tokenId;
		}

		public void setTokenId(String tokenId) {
			this.tokenId = tokenId;
		}

		public String getLocationUrl() {
			return locationUrl;
		}

		public void setLocationUrl(String locationUrl) {
			this.locationUrl = locationUrl;
		}
	}

}
