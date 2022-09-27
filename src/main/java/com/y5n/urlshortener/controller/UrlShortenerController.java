package com.y5n.urlshortener.controller;


import com.y5n.urlshortener.dto.ShortenUrlRequest;
import com.y5n.urlshortener.service.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@Validated
public class UrlShortenerController {

    private final UrlService urlService;

    public UrlShortenerController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/short-url/create")
    public ResponseEntity<?> shortenUrl(HttpServletRequest request, @Valid @RequestBody ShortenUrlRequest shortenUrlRequest) {

        shortenUrlRequest.setClientIp(request.getRemoteAddr());
        String shortUrl = request.getRequestURL().toString().replace(request.getRequestURI(), "")
                + "/" + urlService.shortenUrl(shortenUrlRequest);

        return ResponseEntity.status(HttpStatus.OK).body(shortUrl);
    }
}
