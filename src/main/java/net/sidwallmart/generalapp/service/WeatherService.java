package net.sidwallmart.generalapp.service;

import net.sidwallmart.generalapp.api.response.WeatherResponse;
import net.sidwallmart.generalapp.cache.AppCache;
import net.sidwallmart.generalapp.constants.Placeholders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    private static final String API = "https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city) {

        System.out.println("METHOD CALLED for city = " + city);

        WeatherResponse weatherResponse = redisService.get("weather_of_" + city, WeatherResponse.class);
        if (weatherResponse != null) {
            return weatherResponse;
        }

        String template = appCache.appCache.get(AppCache.keys.WEATHER_API.toString());
        System.out.println("TEMPLATE = " + template);

        String finalAPI = template
                .replace(Placeholders.CITY, city)
                .replace(Placeholders.API_KEY, apiKey);

        System.out.println("🚀 FINAL API = " + finalAPI);

        ResponseEntity<WeatherResponse> response =
                restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);

        System.out.println("RESPONSE = " + response.getBody());

        WeatherResponse body = response.getBody();

        if (body != null) {
            System.out.println("SAVING TO REDIS...");
            redisService.set("weather_of_" + city, body, 300L);
        } else {
            System.out.println("BODY IS NULL");
        }

        return body;
    }
}