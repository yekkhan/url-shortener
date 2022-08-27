package com.y5n.urlshortener.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@Setter
public class Url {
    @Id
    private String shortUrl;

    private String originalUrl;

    private Date createdAt;

    private Date expiredAt;
}
