package com.example.weather.domain;

import javax.validation.constraints.NotNull;

public class UserInput {
    @NotNull
            (message = "name does not exit")
    private String city;

    @NotNull
            (message = "5 digit (US ZIP code")
    private String zip;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public String toString() {
        return "SearchForm{" +
                "city='" + city + '\'' +
                ", zip='" + zip + '\'' +
                '}';
    }
}
