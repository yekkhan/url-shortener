package com.y5n.urlshortener.service;

import java.math.BigInteger;

public interface ConversionService {

    String encode(String originalUrl);

    BigInteger hash(String data);

    String toBase62(BigInteger data);
}
