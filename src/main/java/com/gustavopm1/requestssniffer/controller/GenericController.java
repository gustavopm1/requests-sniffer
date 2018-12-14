package com.gustavopm1.requestssniffer.controller;

import com.gustavopm1.requestssniffer.service.SnifferService;
import com.gustavopm1.requestssniffer.storage.FileSystemStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@Slf4j
public class GenericController {

    @Autowired
    private FileSystemStorage storage;

    @Autowired
    private SnifferService snifferService;

    @RequestMapping("/**")
    public ResponseEntity<?> sniff(HttpServletRequest request) throws IOException {

        log.info("Step 1");

        return snifferService.callThirdPartyApi(request);

    }
}
