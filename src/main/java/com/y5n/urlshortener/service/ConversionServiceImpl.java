package com.y5n.urlshortener.service;

import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class ConversionServiceImpl implements ConversionService {

    private final int base = 62;
    private final String allowedString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    // raw data
    // md5 hash IP address + concat
    // big int

    // presentation
    // base64 encode
    // truncate first 7

    @Override
    public String encode(String originalUrl) {

        //base62 encode hashed result
        BigInteger hashResult = hash(originalUrl);

        return toBase62(hashResult).substring(0, 7);
    }

    @Override
    public BigInteger hash(String data) {
        BigInteger bigIntegerHash;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data.getBytes());
            byte[] digest = md.digest(); //digest.length == 16, 128 bits = 16 bytes = 32 hex digit

            String hash = DatatypeConverter
                    .printHexBinary(digest).toUpperCase();
            bigIntegerHash = new BigInteger(hash, 16);

            return bigIntegerHash;
        } catch (NoSuchAlgorithmException exception) {
            return null;
        }
    }

    public String toBase62(BigInteger input) {
        StringBuilder encodedString = new StringBuilder();
        BigInteger zero = new BigInteger("0");
        BigInteger base = new BigInteger(String.valueOf(this.base));

        while(input.compareTo(zero) == 1) {
            BigInteger remainder = input.remainder(base);
            encodedString.append(allowedString.charAt(remainder.intValue()));
            input = input.divide(base);
        }

        return encodedString.reverse().toString();
    }
}
