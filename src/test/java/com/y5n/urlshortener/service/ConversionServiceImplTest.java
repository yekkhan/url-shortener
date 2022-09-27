package com.y5n.urlshortener.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class ConversionServiceImplTest {

    private ConversionService underTest;
//    private SimpleDateFormat dateTimeFormat;

    @BeforeEach
    void setUp() {
        underTest = new ConversionServiceImpl();
//        dateTimeFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
//        dateTimeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Test
    void test_hash_should_compute_hash_of_ipAddress_and_created_at() {
        // arrange
//        String ipAddress = "66.228.7.37";
//        Date date = dateTimeFormat.parse("15-JAN-2022 17:56:22");
        String originalUrl = "y5n.com";

        BigInteger expected = new BigInteger("78bbdc14f681b896f80ad823783e90ae", 16);

        // act
        BigInteger actual = underTest.hash(originalUrl);

        // assert
        assertEquals(expected, actual);
    }

    @Test
    void test_toBase62_should_encode_given_data() {
        // arrange
        BigInteger data = new BigInteger("78bbdc14f681b896f80ad823783e90ae", 16);

        String expected = "3fovk8u98iXitQ7Jvlixji";

        // act
        String actual = underTest.toBase62(data);

        // assert
        assertEquals(expected, actual);
    }

    @Test
    void test_encode_should_return_first_seven_characters_of_encode_result() {
        // arrange
        String originalUrl = "y5n.com";

        String expected = "3fovk8u";

        // act
        String actual = underTest.encode(originalUrl);

        // assert
        assertEquals(expected, actual);
    }
//
//    @Test
//    void test_generateData_should_compute_hash_of_ipAddress_and_created_at() throws Exception {
//
//        // arrange
//        ConversionServiceImpl conversionService = new ConversionServiceImpl();
//        String clientIp = "66.228.7.37";
//        Date createdAt = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss")
//                .parse("15-JAN-2022 17:56:22");
//
//        // act
//        BigInteger actual = conversionService.generateData(clientIp, createdAt);
//
//        // assert
//        BigInteger expected = new BigInteger("1231234");
//        assertEquals(expected, actual);
//
//    }
}