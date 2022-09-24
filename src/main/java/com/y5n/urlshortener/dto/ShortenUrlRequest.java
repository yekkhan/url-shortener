package com.y5n.urlshortener.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ShortenUrlRequest {

    @NotBlank(message = "The original url is required.")
    private String originalUrl;

    private String expirationDate;

    private String clientIp;
}
