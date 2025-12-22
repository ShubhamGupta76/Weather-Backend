package com.weather.app.weather_App.Model;

import lombok.Data;
import java.util.List;

@Data
public class ForecastResponse {
    private List<ForecastItem> list;
    private City city;

    @Data
    public static class ForecastItem {
        private long dt; // Unix timestamp
        private String dt_txt; // Date and time string
        private Main main;
        private List<Weather> weather;
        private Wind wind;

        @Data
        public static class Main {
            private double temp;
            private double feels_like;
            private int humidity;
            private double temp_min;
            private double temp_max;
            private int pressure;
        }

        @Data
        public static class Weather {
            private String description;
            private String icon;
            private String main;
        }

        @Data
        public static class Wind {
            private double speed;
            private int deg;
        }
    }

    @Data
    public static class City {
        private String name;
        private String country;
    }
}

