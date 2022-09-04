package com.y5n.urlshortener.service;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ConversionServiceImplTest {

    private final ConversionService conversionService;

    public ConversionServiceImplTest() {
        this.conversionService = new ConversionServiceImpl();
    }

    @Test
    void encode() {
        long testDate = 1661587528310L;
        String expectedResult = "eXilSZ8XYmVTNFF4OjPeuF";

        String encodedResult = conversionService.encode("66.228.7.37", new Date(testDate), "y5n.com");
        assertEquals(expectedResult, encodedResult);
    }
}