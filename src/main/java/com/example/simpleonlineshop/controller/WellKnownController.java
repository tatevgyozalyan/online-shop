package com.example.simpleonlineshop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WellKnownController {

    @GetMapping("/.well-known/appspecific/com.chrome.devtools.json")
    public ResponseEntity<Void> handleDevToolsRequest() {
        return ResponseEntity.noContent().build(); // return 204 No Content
    }
}

