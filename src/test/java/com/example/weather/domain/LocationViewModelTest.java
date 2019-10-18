package com.example.weather.domain;

import com.example.weather.controller.LocationViewModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

public class LocationViewModelTest {
    private LocationViewModel weatherLocationViewModel;
    private String zip = "02148";

    @Before
    public void setup() throws Exception {
        weatherLocationViewModel = new LocationViewModel();
        weatherLocationViewModel.setZip(zip);
    }

    @After
    public void tearDown() {
        weatherLocationViewModel = null;
    }

    @Test
    public void verifyCreateWeatherSearchFormObject(){
        assertNotNull(weatherLocationViewModel);
    }

    @Test
    public void verifyCity(){
        assertThat(weatherLocationViewModel.getZip()).isEqualTo(zip);
    }
}