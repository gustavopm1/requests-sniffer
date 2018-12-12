package com.gustavopm1.requestssniffer.service;

import com.gustavopm1.requestssniffer.feign.SnifferClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SnifferService {

    @Autowired
    public SnifferClient snifferClient;


    public Object snifferGetMovieById(String movieId, String apiKey){
        ResponseEntity<Object> movieById = snifferClient.getMovieById(movieId, apiKey);
        return movieById.getBody();
    }

}
