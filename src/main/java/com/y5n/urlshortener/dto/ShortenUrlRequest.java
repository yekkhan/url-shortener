package com.y5n.urlshortener.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ShortenUrlRequest {

    @NotEmpty
    private String originalUrl;

    @NotEmpty
    private Date expirationDate;
}
