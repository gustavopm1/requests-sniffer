package com.gustavopm1.requestssniffer.controller;

import com.gustavopm1.requestssniffer.storage.FilesystemStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class GenericController {

    @Autowired
    private FilesystemStorage storage;

    @RequestMapping("/**")
    public ResponseEntity<?> sniff(HttpServletRequest request, @RequestBody Object body){

        log.info("Step 1");

        RestTemplate restTemplate = new RestTemplate();

        return ResponseEntity.ok(restTemplate.getForObject(request.getRequestURI(),Object.class));

    }
}
