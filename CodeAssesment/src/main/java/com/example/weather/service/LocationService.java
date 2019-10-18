package com.example.weather.service;

import com.example.weather.ApiProperties;
import com.example.weather.domain.Location;
import com.example.weather.exception.OWMResponseErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@Service
public class LocationService {

    private static final Logger logger = LoggerFactory.getLogger(LocationService.class);

    private final String locUrl;

    private final RestTemplate restTemplate;

    private final String authId;
    private final String tokenId;

    public LocationService(RestTemplate restTemplate, ApiProperties properties) {
        this.restTemplate = restTemplate;
        this.restTemplate.setErrorHandler(new OWMResponseErrorHandler());
        this.authId = properties.getApi().getAuthid();
        this.tokenId=properties.getApi().getTokenid();
        this.locUrl = properties.getApi().getLocurl();
    }

    @Cacheable("location")
    public List<Location> getLocation(String zip){
        logger.debug("Requesting current location for {}", zip);
        logger.debug("API loc_Url {} , loc_authId {} , loc_tokenId {}", locUrl, authId, tokenId);
        List<Location> locations = null;
        if(validParameters(zip)) {
            URI url = new UriTemplate(this.locUrl).expand(this.authId, this.tokenId, zip);
            System.out.println("LocationService: url is "+url);
            locations = Arrays.asList(invoke(url, Location[].class));
    }
        return locations;
    }

    private boolean validParameters(String zip) {
        return  zip !=null && !"".equals(zip) &&
                authId !=null && !"".equals(authId) &&
                tokenId!=null && !"".equals(tokenId) &&
                locUrl !=null && !"".equals(locUrl);
    }

    private <T> T invoke(URI url, Class<T> responseType){
        T respondingClass = null;
        try {
            RequestEntity<?> request = RequestEntity.get(url)
                    .accept(MediaType.APPLICATION_JSON).build();
            ResponseEntity<T> exchange = this.restTemplate
                    .exchange(request, responseType);
            respondingClass = exchange.getBody();

        } catch(Exception e){
            logger.error("An error occurred while calling smartystreets.com API endpoint:  " + e.getMessage());
        }

        return respondingClass;
    }
}
