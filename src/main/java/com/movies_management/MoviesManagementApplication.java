package com.movies_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MoviesManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoviesManagementApplication.class, args);
	}

}
