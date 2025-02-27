package com.miu.teo.book.cloudnativespring.catalogservice.controller;

import com.miu.teo.book.cloudnativespring.catalogservice.config.PolarProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    private final PolarProperties polarProperties;

    public HomeController(PolarProperties polarProperties) {
        this.polarProperties = polarProperties;
    }


    @GetMapping
    public String getGreeting(){
        return polarProperties.getGreeting();
    }
}
