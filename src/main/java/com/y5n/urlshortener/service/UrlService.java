package com.y5n.urlshortener.service;

public interface UrlService {

    public String getOriginalUrl(String shortUrl);
    public String shortenUrl(String originalUrl);
}
