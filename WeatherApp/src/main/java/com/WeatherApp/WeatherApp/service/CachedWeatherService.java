package com.WeatherApp.WeatherApp.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CachedWeatherService {
    private WeatherService weatherService;

    public CachedWeatherService(WeatherService weatherService){
        this.weatherService = weatherService;
    }
    @Cacheable(value = "weather",key = "#location")
    public String getCachedWeather(String location){
        return weatherService.getWeather(location);
    }
}
