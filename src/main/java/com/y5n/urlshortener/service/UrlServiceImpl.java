package com.y5n.urlshortener.service;

import com.y5n.urlshortener.dto.ShortenUrlRequest;
import com.y5n.urlshortener.entity.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.Date;

@Service
public class UrlServiceImpl implements UrlService{

    private final ConversionService conversionService;

    public UrlServiceImpl(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public String getUrl(String link) {

        //check redis with shortUrl as key, if not check database, then save in redis
        //if doesn't exist, throw error
        return null;
    }

    @Override
    public String shortenUrl(ShortenUrlRequest request) {
        Url url = new Url();
        String originalUrl = request.getOriginalUrl();
        Date expirationDate = new Date(Long.parseLong(request.getExpirationDate()));
        String clientIp = request.getClientIp();

        url.setOriginalUrl(originalUrl);
        url.setCreatedAt(new Date());
        url.setExpiredAt(expirationDate);

        String encodedResult = conversionService.encode(clientIp, url.getCreatedAt(), originalUrl);

        if(encodedResult == null)
            return null;

        String shortUrl = encodedResult.substring(0, 7);
        url.setShortUrl(shortUrl);

//        urlEntity.save(url);

        return shortUrl;
    }
}
