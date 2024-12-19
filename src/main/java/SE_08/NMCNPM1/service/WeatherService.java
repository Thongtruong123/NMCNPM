package SE_08.NMCNPM1.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private final RestTemplate restTemplate;

    @Autowired
    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private JsonNode fetchWeatherData() {
        String url = "http://api.openweathermap.org/data/2.5/weather?q=Hanoi&appid=9ec25e4d7356e98c10ceb1c9c6688688&units=metric&lang=vi";
        try {
            String jsonResponse = restTemplate.getForObject(url, String.class);
            return new com.fasterxml.jackson.databind.ObjectMapper().readTree(jsonResponse);
        } catch (Exception e) {
            return null;
        }
    }

    public Double getTemperature() {
        JsonNode root = fetchWeatherData();
        if (root != null) {
            return root.path("main").path("temp").asDouble();
        }
        return null;
    }

    public String getDescription() {
        JsonNode root = fetchWeatherData();
        if (root != null) {
            return root.path("weather").get(0).path("description").asText();
        }
        return null;
    }

    public String getIcon() {
        JsonNode root = fetchWeatherData();
        if (root != null) {
            return root.path("weather").get(0).path("icon").asText();
        }
        return null;
    }

    public int getWeatherId() {
        JsonNode root = fetchWeatherData();
        if (root != null) {
            return root.path("weather").get(0).path("id").asInt();
        }
        return -1;
    }

    public String getBackgroundColor() {
        int id = getWeatherId();
        switch (id / 100) {
            case 2: return "linear-gradient(to bottom, #4b4f6c, #a6b1c5)"; // Thunderstorm
            case 3: return "linear-gradient(to bottom, #3c7b8b, #a3c7e0)"; // Drizzle
            case 5: return "linear-gradient(to bottom, #1b5b7e, #69c2e8)"; // Rain
            case 6: return "linear-gradient(to bottom, #80deea, #ffffff)"; // Snow
            case 7:
                if (id <= 741) {
                    return "linear-gradient(to bottom, #8c999b, #d2e5e2)"; // Fog
                } else {
                    return "linear-gradient(to bottom, #e1c6b7, #f7f9f5)"; // Dust
                }
            case 8:
                if (id == 800) {
                    return "linear-gradient(to bottom, #51b8e1, #f1f9f4)"; // Clear sky
                } else {
                    return "linear-gradient(to bottom, #9ec5e0, #f4f8fb)"; // Cloudy
                }
            default: return "white";
        }
    }
}
