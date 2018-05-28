package com.bootcamp2018.shoppingapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan({"com.bootcampglobant.userregister.models", "com.bootcamp2018.shoppingapi.model"})
@ComponentScan({"com.bootcampglobant.userregister", "com.bootcamp2018.shoppingapi.model",
		"com.bootcamp2018.shoppingapi.controller", "com.bootcamp2018.shoppingapi.repository",
		"com.bootcamp2018.shoppingapi.service"})
public class ShoppingapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoppingapiApplication.class, args);
    }
}
