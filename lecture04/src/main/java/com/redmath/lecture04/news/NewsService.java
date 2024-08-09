package com.redmath.lecture04.news;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class NewsService {

    private final NewsApiClient newsApiClient;

    private final NewsRepository newsRepository;

    @Autowired
    public NewsService(NewsRepository newsRepository, NewsApiClient newsApiClient) {
        this.newsRepository = newsRepository;
        this.newsApiClient = newsApiClient;
    }

    public Optional<News> findById(Long newsId) {
        return newsRepository.findById(newsId);
    }

    public List<News> findAll(Integer page, Integer size) {
        if (page < 0) {
            page = 0;
        }
        if (size > 1000) {
            size = 1000;
        }
        return newsRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    public News create(News news) {
        news.setNewsId(System.currentTimeMillis());
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        news.setReportedBy(username);
        news.setReportedAt(LocalDateTime.now());
        return newsRepository.save(news);
    }

    public Optional<News> update(Long newsId, News news) {
        Optional<News> existing = newsRepository.findById(newsId);
        if (existing.isPresent()) {
            existing.get().setTitle(news.getTitle());
            existing.get().setDetails(news.getDetails());
            existing = Optional.of(newsRepository.save(existing.get()));
        }
        return existing;
    }

//    @Scheduled(fixedDelay = 5000) // Schedule to run every hour
//    public CompletableFuture<List<News>> fetchBitcoinNewsPeriodically() {
//        return newsApiClient.fetchBitcoinNews();
//    }


}
