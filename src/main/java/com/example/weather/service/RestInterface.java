package com.example.weather.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

public  class RestInterface {
    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

   public static <T> T invoke(RestTemplate restTemplate,URI url, Class<T> responseType)
   {
       T result=null;
       try {
           RequestEntity<?> request = RequestEntity.get(url)
                   .accept(MediaType.APPLICATION_JSON).build();
           ResponseEntity<T> exchange = restTemplate
                   .exchange(request, responseType);
           result = exchange.getBody();
       } catch(Exception e){
           logger.error("An error occurred while calling openweathermap.org API endpoint:  " + e.getMessage());
       }

       return result;
   }

}
