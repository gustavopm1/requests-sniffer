package com.gustavopm1.requestssniffer.controller;

import com.gustavopm1.requestssniffer.service.SnifferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping(value = "/movie")
@Slf4j
public class SnifferController {

    @Autowired
    public SnifferService snifferService;

    @GetMapping(value = "/{movieId}")
    public Object getMovieById(@PathVariable("movieId") String movieId, @RequestParam("api_key") String apiKey,
            HttpServletRequest request) {

        String path = request.getRequestURI() + "/" + request.getQueryString();
        Object o = null;
        try {
            o = snifferService.snifferGetMovieById(movieId, apiKey, path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return o;
    }


}
