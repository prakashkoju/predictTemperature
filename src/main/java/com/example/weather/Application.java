package com.example.weather;

import com.example.weather.apiProperty.ApiProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableConfigurationProperties(ApiProperty.class)
public class Application {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@ConditionalOnBean
    public ApiProperty apiProperty(){
	    return new ApiProperty();
    }

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
