package com.y5n.urlshortener.controller;


import com.y5n.urlshortener.dto.ShortenUrlRequest;
import com.y5n.urlshortener.service.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@RestController
@RequestMapping("/")
public class UrlShortenerController {

    private final UrlService urlService;

//    private final String domain = "http://y5n.com/";
    private final String domain = "localhost:8080/";

    public UrlShortenerController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/api/v1/short-url/create")
    public ResponseEntity shortenUrl(HttpServletRequest request, @RequestBody ShortenUrlRequest shortenUrlRequest) {

        shortenUrlRequest.setClientIp(request.getRemoteAddr());
        String shortUrl = domain + urlService.shortenUrl(shortenUrlRequest);

        return ResponseEntity.status(HttpStatus.OK).body(shortUrl);
    }

    @GetMapping("{link}")
    public ResponseEntity getAndRedirect(@PathVariable String link) {

        String originalUrl = urlService.getOriginalUrl(link);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }
}
