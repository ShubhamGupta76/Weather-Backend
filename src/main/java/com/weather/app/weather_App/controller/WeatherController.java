package com.weather.app.weather_App.controller;

import org.springframework.web.bind.annotation.*;

import com.weather.app.weather_App.Model.WeatherResponse;
import com.weather.app.weather_App.services.WeatherService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/{city}")
    public WeatherResponse getWeather(@PathVariable String city) {
        return weatherService.getWeather(city);
    }
}