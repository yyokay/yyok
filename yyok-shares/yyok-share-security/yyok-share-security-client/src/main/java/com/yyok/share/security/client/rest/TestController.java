package com.yyok.share.security.client.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @GetMapping("/client/{clientId}")
    public String getClient(@PathVariable String clientId) {
        return clientId;
    }

}
