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
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
            Map<String, String> headers = Collections.list(((HttpServletRequest) request).getHeaderNames())
                    .stream()
                    .collect(Collectors.toMap(h -> h, request::getHeader));

            responseEntity = restTemplate.exchange(url,
                    HttpMethod.valueOf(request.getMethod()),
                    new HttpEntity(httpHeaders),
                    Object.class);

            Map<String, Object> headersInfo = getHeadersInfo(responseEntity.getHeaders());

            fileSystemStorage.persistRequest(url, headersInfo, responseEntity.getBody());
        } else {
            BufferBody bufferBody = fileSystemStorage.loadRequest(url);
            return new ResponseEntity(bufferBody, HttpStatus.OK);
        }
        return responseEntity;
    }

    private Map<String, Object> getHeadersInfo(HttpHeaders request) {

        Map<String, Object> map = new HashMap<>();

        request.keySet().stream().forEach(s -> {
            String s1 = request.get(s).get(0);
            map.put(s, s1);
        });
        return map;
    }

}
