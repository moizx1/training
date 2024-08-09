package com.redmath.lecture03.news;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
public class NewsController {

    @Autowired
    private NewsApiClient newsApiClient;

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/api/v1/news/{newsId}")
    public ResponseEntity<News> get(@PathVariable("newsId") Long newsId) {
        Optional<News> news = newsService.findById(newsId);
        if (news.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(news.get());
    }

    @GetMapping("/api/v1/news")
    public ResponseEntity<List<News>> get(@RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "1000") Integer size) {
        return ResponseEntity.ok(newsService.findAll(page, size));
    }

    @PreAuthorize("hasAnyAuthority('admin', 'reporter')")
    @PostMapping("/api/v1/news")
    public ResponseEntity<News> create(@RequestBody News news) {
        news = newsService.create(news);
        return ResponseEntity.created(URI.create("/api/v1/news/" + news.getNewsId())).body(news);
    }

    @PreAuthorize("hasAnyAuthority('admin', 'editor')")
    @PutMapping("/api/v1/news/{newsId}")
    public ResponseEntity<News> update(@PathVariable("newsId") Long newsId, @RequestBody News news) {
        Optional<News> saved = newsService.update(newsId, news);
        if (saved.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(saved.get());
    }

    @GetMapping("/api/v1/news/bitcoin")
    public CompletableFuture<List<News>> get() {
        return newsService.fetchBitcoinNewsPeriodically();
    }
}
