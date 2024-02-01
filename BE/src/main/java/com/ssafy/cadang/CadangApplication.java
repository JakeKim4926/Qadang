package com.ssafy.cadang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CadangApplication {

	public static void main(String[] args) {
		SpringApplication.run(CadangApplication.class, args);
	}

}
