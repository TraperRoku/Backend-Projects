package com.WeatherApp.WeatherApp.controller;

import com.WeatherApp.WeatherApp.service.CachedWeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {
    private final CachedWeatherService cachedWeatherService;


    public WeatherController(CachedWeatherService cachedWeatherService){
        this.cachedWeatherService =cachedWeatherService;
    }

    @GetMapping("/weather/{location}")
    public String getWeather(@PathVariable String location){
        return cachedWeatherService.getCachedWeather(location);
    }
}
