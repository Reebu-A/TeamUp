package com.Teamup.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.Teamup.model.WeatherResponse;


@Service
public class Weatherservice 
{
	private final String apiKey = "24b81bdac2cbf2c8d1a9d209302073e7";
    private final String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=Thiruvananthapuram,in&appid=" + apiKey + "&units=metric";

    public WeatherResponse getWeatherData() {
        RestTemplate restTemplate = new RestTemplate();
        WeatherResponse response = restTemplate.getForObject(apiUrl, WeatherResponse.class);

        // Extract data from response object
        String cityName = response.getName();
        double temperature = response.getMain().getTemp();
        int humidity = response.getMain().getHumidity();
        String description = response.getWeather()[0].getDescription();

        // Create a weather report string
        String weatherReport = String.format("City: %s, Temperature: %.2f°C, Humidity: %d%%, Description: %s",
                cityName, temperature, humidity, description);

        return response;
    }

}
