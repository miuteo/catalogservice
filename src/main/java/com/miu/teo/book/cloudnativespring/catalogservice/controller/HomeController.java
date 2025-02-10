package com.miu.teo.book.cloudnativespring.catalogservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {


    @GetMapping
    public String getGreeting(){
        return "welcome to the book catalog";
    }
}
