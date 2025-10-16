package com.weather.app.weather_App.Model;

import lombok.Data;
import java.util.List;

@Data
public class WeatherResponse {
    private Main main;
    private List<Weather> weather;
    private String name;

    @Data
    public static class Main {
        private double temp;
        private double feels_like;
        private int humidity;
    }

    @Data
    public static class Weather {
        private String description;
        private String icon;
    }
}
