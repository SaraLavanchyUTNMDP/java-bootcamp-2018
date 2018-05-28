package com.bootcamp2018.shoppingapi.config;

import com.bootcamp2018.shoppingapi.model.ShoppingCart;
import com.bootcamp2018.shoppingapi.service.CartService;
import com.bootcampglobant.userregister.controller.UserService;
import com.bootcampglobant.userregister.models.User;
import com.bootcampglobant.userregister.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigClass {

		@Bean
		public UserService UserService(){
			return new UserService();
		}

		@Bean
		public User user(){
			return  new User();
		}

		@Bean
		public CartService cartService(){
			return  new CartService();
		}
}
