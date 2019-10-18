package com.example.weather.domain;

import java.util.Objects;

public class Zipcode {
    private String default_city;
    private String zipcode;


    public String getDefault_city() {
        return default_city;
    }

    public void setDefault_city(String default_city) {
        this.default_city = default_city;
    }


    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public String toString() {
        return "Location{" +
                "default_city='" + default_city + '\'' +
                ", zipcode='" + zipcode + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zipcode zipcode1 = (Zipcode) o;
        return Objects.equals(default_city, zipcode1.default_city) &&
                Objects.equals(zipcode, zipcode1.zipcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(default_city, zipcode);
    }
}
