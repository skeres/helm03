package com.sks.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloControler {

    //@Value("${server.name.variable}")
    //private String hostname;

    @GetMapping("/hello")
    public String index() {
        return "Greetings from Spring Boot ! writing from customer controller ";
    }

}