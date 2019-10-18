package com.example.weather.service;

import com.example.weather.domain.Weather;

public interface IWeatherService {
   public Weather getWeather(String city);
}
