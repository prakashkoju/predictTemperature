package com.example.weather.service;

import com.example.weather.domain.Location;

import java.util.List;

public interface ILocationService {
    public List<Location> getLocation(String zip);
}
