package com.toufik.service;

import com.toufik.model.news.NewsResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NewsService {

    private final RestTemplate restTemplate;
    private final String url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=e4d6dfb727984b11a7662b061c8d8bda";

    public NewsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public NewsResponse getLatestNews() {
        String apiUrl = "https://newsapi.org/v2/everything?q=tesla&from=2024-07-18&sortBy=publishedAt&apiKey=e4d6dfb727984b11a7662b061c8d8bda";
        return restTemplate.getForObject(this.url, NewsResponse.class);
    }
}
