package com.example.weather.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

public class UserInputTest {
    private UserInput weatherUserInput;
    private String zip = "02148";

    @Before
    public void setup() throws Exception {
        weatherUserInput = new UserInput();
        weatherUserInput.setZip(zip);
    }

    @After
    public void tearDown() {
        weatherUserInput = null;
    }

    @Test
    public void verifyCreateWeatherSearchFormObject(){
        assertNotNull(weatherUserInput);
    }

    @Test
    public void verifyCity(){
        assertThat(weatherUserInput.getZip()).isEqualTo(zip);
    }
}