package com.example.weather.service;

import com.example.weather.apiProperty.ApiProperty;
import com.example.weather.domain.Weather;
import com.example.weather.exception.OWMResponseErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.net.URI;

@Service
public class WeatherService implements IWeatherService {

	private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

	private final String apiUrl;

	private final RestTemplate restTemplate;

	private final String apiKey;

	public WeatherService(RestTemplate restTemplate, ApiProperty properties) {
		this.restTemplate = restTemplate;
		this.restTemplate.setErrorHandler(new OWMResponseErrorHandler());
		this.apiKey = properties.getApi().getKey();
		this.apiUrl = properties.getApi().getUrl();
	}

	@Cacheable("weather")
	public Weather getWeather(String city){
		logger.debug("Requesting current weather for {}", city);
		logger.debug("API key {} , url {}", apiKey, apiUrl);
		Weather weather = null;
		if(validParameters(city)) {
			URI url = new UriTemplate(this.apiUrl).expand(city, this.apiKey);

			weather = RestInterface.invoke(restTemplate,url, Weather.class);
		}
		return weather;
	}

	private boolean validParameters(String city) {
		return city !=null && !"".equals(city) && apiKey !=null && !"".equals(apiKey) && apiUrl!=null && !"".equals(apiUrl);
	}

}
