package com.y5n.urlshortener.controller;


import com.y5n.urlshortener.service.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/")
public class UrlShortenerController {

    private final UrlService urlService;

    public UrlShortenerController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("{shortUrl}")
    public ResponseEntity getAndRedirect(@PathVariable String shortUrl) {

        String url = urlService.getOriginalUrl(shortUrl);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(url))
                .build();
    }
}
