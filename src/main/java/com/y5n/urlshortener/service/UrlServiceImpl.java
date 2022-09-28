package com.y5n.urlshortener.service;

import com.y5n.urlshortener.dto.ShortenUrlRequest;
import com.y5n.urlshortener.entity.Url;
import com.y5n.urlshortener.exception.UrlNotFoundException;
import com.y5n.urlshortener.repository.UrlRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UrlServiceImpl implements UrlService{

    private final ConversionService conversionService;
    private final UrlRepository urlRepository;
    private final RedisTemplate<String, String> redisTemplate;

    public UrlServiceImpl(ConversionService conversionService, UrlRepository urlRepository, RedisTemplate<String, String> redisTemplate) {
        this.conversionService = conversionService;
        this.urlRepository = urlRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String getOriginalUrl(String link) {

        //check redis with shortUrl as key, if not check database, then save in redis
        //if doesn't exist, throw error

        String originalUrl = redisTemplate.opsForValue().get(link);

        if(originalUrl != null) {
            log.info("Get Original Url Cache Hit: " +  link);
            return originalUrl;
        } else {
            log.info("Get Original Url Cache miss");

            if (urlRepository.existsById(link)) {
                originalUrl = urlRepository.findById(link).isPresent()
                        ? urlRepository.findById(link).get().getOriginalUrl()
                        : null;

                if(originalUrl != null) {
                    redisTemplate.opsForValue().set(link, originalUrl);
                    redisTemplate.expire(link, 10, TimeUnit.MINUTES);

                    return originalUrl;
                }
            }
        }

        throw new UrlNotFoundException("/" + link + " is invalid");
    }

    @Override
    public String shortenUrl(ShortenUrlRequest request) {
        String originalUrl = request.getOriginalUrl();
        if(urlRepository.existsByOriginalUrl(originalUrl)) {
            return urlRepository.findByOriginalUrl(originalUrl).getShortUrl();
        }

//        Date expirationDate = request.getExpirationDate() != null ? new Date(Long.parseLong(request.getExpirationDate())) : null;

        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setCreatedAt(new Date());
//        url.setExpiredAt(expirationDate);

        String shortUrl = conversionService.encode(originalUrl);

        if(shortUrl == null)
            return null;

        url.setShortUrl(shortUrl);

        urlRepository.save(url);

        return shortUrl;
    }
}
