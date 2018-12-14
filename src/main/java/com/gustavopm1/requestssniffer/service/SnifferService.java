package com.gustavopm1.requestssniffer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gustavopm1.requestssniffer.feign.SnifferClient;
import com.gustavopm1.requestssniffer.model.RequestEntity;
import com.gustavopm1.requestssniffer.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class SnifferService {

    @Value("${enable-mock}")
    private Boolean enableMock;

    @Autowired
    public SnifferClient snifferClient;

    @Autowired
    public RequestRepository requestRepository;

    @Autowired
    private ObjectMapper objectMapper;


    public Object snifferGetMovieById(String movieId, String apiKey, String path) throws IOException {

        Object body = null;

        if (enableMock) {
            Optional<RequestEntity> requestEntityOptional = requestRepository.findById(path);

            RequestEntity requestEntity;

            if (requestEntityOptional.isPresent()) {
                requestEntity = requestEntityOptional.get();
                body = objectMapper.readValue(requestEntity.getBody(), Object.class);
            }

        } else {
            ResponseEntity<Object> movieById = snifferClient.getMovieById(movieId, apiKey);
            body = movieById.getBody();

            String requestEntityJson = objectMapper.writeValueAsString(body);
            RequestEntity requestEntity = new RequestEntity(path, requestEntityJson);
            requestRepository.save(requestEntity);
        }
        return body;
    }

}
