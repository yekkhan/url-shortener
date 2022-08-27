package com.y5n.urlshortener.service;

import com.y5n.urlshortener.dto.ShortenUrlRequest;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UrlServiceImpl implements UrlService{
    @Override
    public String getUrl(String link) {

        //check redis with shortUrl as key, if not check database, then save in redis
        //if doesn't exist, throw error
        return null;
    }

    @Override
    public String shortenUrl(ShortenUrlRequest request) {

        String originalUrl = request.getOriginalUrl();
        Date expirationDate = request.getExpirationDate();

        //md5
        //base 62
        //store in db
        return null;
    }
}
