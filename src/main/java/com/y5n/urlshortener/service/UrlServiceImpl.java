package com.y5n.urlshortener.service;

import com.y5n.urlshortener.dto.ShortenUrlRequest;
import com.y5n.urlshortener.entity.Url;
import com.y5n.urlshortener.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.Date;
import java.util.Optional;

@Service
public class UrlServiceImpl implements UrlService{

    private final ConversionService conversionService;
    private final UrlRepository urlRepository;

    public UrlServiceImpl(ConversionService conversionService, UrlRepository urlRepository) {
        this.conversionService = conversionService;
        this.urlRepository = urlRepository;
    }

    @Override
    public String getOriginalUrl(String link) {

        //check redis with shortUrl as key, if not check database, then save in redis
        //if doesn't exist, throw error
        if (!urlRepository.existsById(link))
            return null;

        return urlRepository.findById(link).isPresent()
                ? urlRepository.findById(link).get().getOriginalUrl()
                : null;
    }

    @Override
    public String shortenUrl(ShortenUrlRequest request) {
        String originalUrl = request.getOriginalUrl();
        Date expirationDate = request.getExpirationDate() != null ? new Date(Long.parseLong(request.getExpirationDate())) : null;
        String clientIp = request.getClientIp();

        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setCreatedAt(new Date());
        url.setExpiredAt(expirationDate);

        String encodedResult = conversionService.encode(clientIp, url.getCreatedAt(), originalUrl);

        if(encodedResult == null)
            return null;

        String shortUrl = encodedResult.substring(0, 7);
        url.setShortUrl(shortUrl);

        urlRepository.save(url);

        return shortUrl;
    }
}
