package com.y5n.urlshortener.service;

import java.math.BigInteger;
import java.util.Date;

public interface ConversionService {

    String encode(String ipAddress, Date createdAt, String originalUrl);

    String preprocessData(String ipAddress, Date date, String originalUrl);

    BigInteger hash(String data);

    String toBase62(BigInteger data);
}
