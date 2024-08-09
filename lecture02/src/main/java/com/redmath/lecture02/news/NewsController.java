package com.redmath.lecture02.news;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class NewsController {
    private final NewsRepository newsRepository;

    public NewsController(NewsRepository newsRepository){
        this.newsRepository=newsRepository;
    }

    @GetMapping("/api/v1/news/{newsId}")
    public ResponseEntity<News> get(@PathVariable("newsId") Long newsId){
        Optional<News> news = newsRepository.findById(newsId);
        if(news.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(news.get());
    }

    @GetMapping("/api/v1/news")
    public ResponseEntity<List<News>> get(@RequestParam(name="page", defaultValue = "0") Integer page, @RequestParam(name="page", defaultValue = "1000") Integer size ){
        return ResponseEntity.ok(newsRepository.findAll());
    }

}
