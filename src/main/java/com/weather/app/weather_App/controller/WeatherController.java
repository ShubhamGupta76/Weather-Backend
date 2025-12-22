package com.weather.app.weather_App.controller;

import org.springframework.web.bind.annotation.*;

import com.weather.app.weather_App.Model.WeatherResponse;
import com.weather.app.weather_App.Model.ForecastResponse;
import com.weather.app.weather_App.services.WeatherService;

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

    @GetMapping("/{city}/forecast")
    public ForecastResponse getForecast(@PathVariable String city) {
        return weatherService.getForecast(city);
    }
}