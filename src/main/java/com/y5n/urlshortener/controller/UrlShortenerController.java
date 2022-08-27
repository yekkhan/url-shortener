package com.y5n.urlshortener.controller;


import com.y5n.urlshortener.dto.ShortenUrlRequest;
import com.y5n.urlshortener.service.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/")
public class UrlShortenerController {

    private final UrlService urlService;

    public UrlShortenerController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/api/v1/short-url/create")
    public String shortenUrl(@RequestBody ShortenUrlRequest request) {

        return urlService.shortenUrl(request);
    }

    @GetMapping("{link}")
    public ResponseEntity getAndRedirect(@PathVariable String link) {

        String url = urlService.getUrl(link);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(url))
                .build();
    }
}
