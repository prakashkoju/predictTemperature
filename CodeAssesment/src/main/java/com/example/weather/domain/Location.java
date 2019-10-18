package com.example.weather.domain;

import java.util.ArrayList;

public class Location {

    public ArrayList<Zipcode> zipcodes;

    public Location()
    {
        zipcodes=new ArrayList<Zipcode>();
    }

    public ArrayList<Zipcode> getZipcodes() {
        return zipcodes;
    }

    public void setZipcodes(ArrayList<Zipcode> zipcodes) {
        this.zipcodes = zipcodes;
    }
}
