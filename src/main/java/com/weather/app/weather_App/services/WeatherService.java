package com.weather.app.weather_App.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.weather.app.weather_App.Model.WeatherResponse;
import com.weather.app.weather_App.Model.ForecastResponse;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherResponse getWeather(String city) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q="
                     + city + "&appid=" + apiKey + "&units=metric";

        ResponseEntity<WeatherResponse> response =
                restTemplate.getForEntity(url, WeatherResponse.class);

        return response.getBody();
    }

    public ForecastResponse getForecast(String city) {
        String url = "https://api.openweathermap.org/data/2.5/forecast?q="
                     + city + "&appid=" + apiKey + "&units=metric";

        ResponseEntity<ForecastResponse> response =
                restTemplate.getForEntity(url, ForecastResponse.class);

        return response.getBody();
    }
}