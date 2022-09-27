package com.y5n.urlshortener.controller;

import com.y5n.urlshortener.service.UrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/")
@Slf4j
public class UrlRetrievalController {

    private final UrlService urlService;

    public UrlRetrievalController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("{link}")
    public ResponseEntity<?> getAndRedirect(@PathVariable String link) {
        log.info("Get and Redirect URL");
        String originalUrl = urlService.getOriginalUrl(link);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }
}
