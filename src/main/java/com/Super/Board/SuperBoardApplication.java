package com.Super.Board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SuperBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(SuperBoardApplication.class, args);
	}

}
