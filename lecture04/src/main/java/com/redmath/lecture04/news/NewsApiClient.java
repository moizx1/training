package com.redmath.lecture04.news;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class NewsApiClient {

    private static final Logger logger = LoggerFactory.getLogger(NewsApiClient.class);

    @Value("${newsapi.key}")
    private String apiKey;

    private static final String NEWS_API_URL = "https://newsapi.org/v2/everything?q=bitcoin&apiKey=";

    private final RestTemplate restTemplate;

    public NewsApiClient(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Async
    public CompletableFuture<List<News>> fetchBitcoinNews() {
        String url = NEWS_API_URL + apiKey;
        List<News> customArticles = new ArrayList<>();
        try {
        String response = restTemplate.getForObject(url, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);
            JsonNode articles = root.path("articles");

            for (JsonNode article : articles) {
                News customArticle = new News();
                customArticle.setTitle(article.path("title").asText());
                customArticle.setDetails(article.path("content").asText());
                customArticle.setReportedBy(article.path("author").asText());
                customArticle.setReportedAt(LocalDateTime.parse(article.path("publishedAt").asText(), DateTimeFormatter.ISO_DATE_TIME));
                customArticles.add(customArticle);
            }
        } catch (Exception e) {
            logger.error("Error fetching news from News API: {}", e.getMessage());
            logger.trace("Error fetching news from News API: {}", e.getMessage());
        }
        return CompletableFuture.completedFuture(customArticles);
    }



////    @Cacheable(cacheNames = "weather")
//    @Async
//    public List<News> findBitcoinNews(String search) {
//        String result = restTemplate
//                .getForEntity("https://news.google.com/home?hl=en-PK&gl=PK&ceid=PK:en", String.class).getBody();
//        News news = new News();
//        news.setDetails(result);
//        return List.of(news);
//    }
}
