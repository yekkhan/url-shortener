package com.y5n.urlshortener.service;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ConversionServiceImplTest {

    @Test
    void encode() {
        long testDate = 1661587528310L;
        String expectedResult = "eXilSZ8XYmVTNFF4OjPeuF";
        ConversionService conversionService = new ConversionServiceImpl();
        String encodedResult = conversionService.encode("66.228.7.37", new Date(testDate), "y5n.com");
        assertEquals(expectedResult, encodedResult);
    }

//    @Test
//    void test_playground() throws ParseException {
//        assertEquals(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss")
//                .parse("15-JAN-2022 17:56:22").toString(), "hello");
//    }
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