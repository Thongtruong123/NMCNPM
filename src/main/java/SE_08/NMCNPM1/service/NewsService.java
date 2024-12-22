package SE_08.NMCNPM1.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NewsService {

    private final RestTemplate restTemplate;

    @Autowired
    public NewsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Map<String, String>> getArticles() {
        String url = "https://newsapi.org/v2/everything?q=chung cư&apiKey=98974d82b37d4bb19efb719cf80845ee&language=vi";
        try {
            JsonNode response = restTemplate.getForObject(url, JsonNode.class);
            if (response == null || !response.has("articles")) {
                System.out.println("response empty or no articles field");
                return new ArrayList<>();
            }
            JsonNode articlesNode = response.get("articles");
            List<Map<String, String>> articles = new ArrayList<>();

            for (JsonNode article : articlesNode) {
                Map<String, String> articleMap = new HashMap<>();
                articleMap.put("title", article.path("title").asText(""));
                articleMap.put("description", article.path("description").asText(""));
                articleMap.put("url", article.path("url").asText(""));
                articleMap.put("urlToImage", article.path("urlToImage").asText(""));

                articles.add(articleMap);
            }

            return articles;
        } catch (Exception e) {
            System.out.println("Error fetching articles");
            return null;
        }
    }
}
