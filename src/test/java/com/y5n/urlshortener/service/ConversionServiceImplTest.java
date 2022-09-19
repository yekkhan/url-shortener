package com.y5n.urlshortener.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

class ConversionServiceImplTest {

    private ConversionService underTest;
    private SimpleDateFormat dateTimeFormat;

    @BeforeEach
    void setUp() {
        underTest = new ConversionServiceImpl();
        dateTimeFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        dateTimeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Test
    void test_preprocessData_should_concatenate_ipAddress_and_createdAt() throws ParseException {
        // arrange
        String ipAddress = "66.228.7.37";
        Date date = dateTimeFormat.parse("15-JAN-2022 17:56:22");
        String originalUrl = "y5n.com";

        String expected = ipAddress + date.toInstant() + originalUrl;

        // act
        String actual = underTest.preprocessData(ipAddress, date, originalUrl);

        // assert
        assertEquals(expected, actual);

    }

    @Test
    void test_hash_should_compute_hash_of_ipAddress_and_created_at() throws ParseException {
        // arrange
        String ipAddress = "66.228.7.37";
        Date date = dateTimeFormat.parse("15-JAN-2022 17:56:22");
        String originalUrl = "y5n.com";
        String data = ipAddress + date.toInstant() + originalUrl;

        BigInteger expected = new BigInteger("00db7490cf74d3548171598cd2ea2e75", 16);

        System.out.println("hello");
        System.out.println(data);
        // act
        BigInteger actual = underTest.hash(data);

        // assert
        assertEquals(expected, actual);
    }

    @Test
    void test_toBase62_should_encode_given_data() {
        // arrange
        BigInteger data = new BigInteger("00db7490cf74d3548171598cd2ea2e75", 16);

        String expected = "1cI41Cw8HWiFoaeNsCrQr";

        // act
        String actual = underTest.toBase62(data);

        // assert
        assertEquals(expected, actual);
    }

    @Test
    void test_encode_should_return_first_seven_characters_of_encode_result() throws ParseException {
        // arrange
        String ipAddress = "66.228.7.37";
        Date date = dateTimeFormat.parse("15-JAN-2022 17:56:22");
        String originalUrl = "y5n.com";

        String expected = "1cI41Cw";

        // act
        String actual = underTest.encode(ipAddress, date, originalUrl);

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