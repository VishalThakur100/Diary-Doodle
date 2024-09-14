package net.engineeringdigest.journalApp.services;

import net.engineeringdigest.journalApp.api_Response.WeatherResponse;
import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.constants.Placeholders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    @Value("${weather.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    //  String requestBody="{\"city\":\"San Francisco\",\"country\":\"USA\"}";

    //HttpEntity<String> httpEntity=new HttpEntity<>(requestBody);

    @Autowired
    private AppCache appCache;

    //    public WeatherResponse postWeather(String city) {
//        String finalApi = apiUrl.replace("API_KEY", apiKey).replace("CITY", city);
//        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalApi, HttpMethod.POST, httpEntity, WeatherResponse.class);
//        return response.getBody();
//    }
    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city) {
        WeatherResponse weatherResponse = redisService.get("Weather of" + city, WeatherResponse.class);
        if (weatherResponse != null) {
            return weatherResponse;
        } else {
            String finalApi = appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(Placeholders.API_KEY, apiKey).replace(Placeholders.CITY, city);
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);
            WeatherResponse body = response.getBody();
            if (body != null) {
                redisService.set("Weather of " + city, body, 300l);
            }
            return body;
        }
    }

}
