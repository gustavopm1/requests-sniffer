package com.gustavopm1.requestssniffer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.server.PathParam;

@Component
@FeignClient(name = "Sniffer", url = "${com.gotcamel.baseUrlTMDB}")
public interface SnifferClient {

    @GetMapping(value = "/{movieId}")
    ResponseEntity<Object> getMovieById(@PathVariable("movieId") String movieId, @RequestParam("api_key") String apiKey);
}
