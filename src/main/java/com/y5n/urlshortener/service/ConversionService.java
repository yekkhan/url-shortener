package com.y5n.urlshortener.service;

import java.util.Date;

public interface ConversionService {

    String encode(String clientIp, Date createdAt, String originalUrl);

    String decode();
}
