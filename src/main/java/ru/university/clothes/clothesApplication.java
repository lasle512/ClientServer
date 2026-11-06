package ru.university.clothes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching 
@SpringBootApplication
public class clothesApplication {

	public static void main(String[] args) {
		SpringApplication.run(clothesApplication.class, args);
	}

}
