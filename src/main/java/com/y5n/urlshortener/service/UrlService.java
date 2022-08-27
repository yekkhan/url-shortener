package com.y5n.urlshortener.service;

import com.y5n.urlshortener.dto.ShortenUrlRequest;

public interface UrlService {

    String getUrl(String shortUrl);

    String shortenUrl(ShortenUrlRequest request);
}
