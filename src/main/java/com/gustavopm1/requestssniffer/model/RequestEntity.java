package com.gustavopm1.requestssniffer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@AllArgsConstructor
@RedisHash("Request")
public class RequestEntity {

    @Id
    private String url;

    private String body;
}
