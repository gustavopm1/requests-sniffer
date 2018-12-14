package com.gustavopm1.requestssniffer.service;

import com.gustavopm1.requestssniffer.feign.SnifferClient;
import com.gustavopm1.requestssniffer.models.BufferBody;
import com.gustavopm1.requestssniffer.storage.FileSystemStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;

@Service
public class SnifferService {

    @Autowired
    private SnifferClient snifferClient;

    @Autowired
    private FileSystemStorage fileSystemStorage;

    @Value("${com.gotcamel.baseUrlTMDB}")
    private String baseURL;

    @Value("${enable-mock}")
    private boolean enableMock;

    public Object snifferGetMovieById(String movieId, String apiKey) {
        ResponseEntity<Object> movieById = snifferClient.getMovieById(movieId, apiKey);
        return movieById.getBody();
    }

    public ResponseEntity<Object> callThirdPartyApi(HttpServletRequest request) throws IOException {

        String url = baseURL + request.getRequestURI() + "?" + request.getQueryString();

        HttpHeaders httpHeaders = new HttpHeaders();

        ResponseEntity<Object> responseEntity;

        if (!enableMock) {
            RestTemplate restTemplate = new RestTemplate();
            responseEntity = restTemplate.exchange(url,
                    HttpMethod.valueOf(request.getMethod()),
                    new HttpEntity(httpHeaders),
                    Object.class);

            fileSystemStorage.persistRequest(url, new HashMap<>(), responseEntity.getBody());
        } else {
            BufferBody bufferBody = fileSystemStorage.loadRequest(url);
            return new ResponseEntity(bufferBody, HttpStatus.OK);
        }
        return responseEntity;
    }


}
