package com.osm.findarea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FindAreaApplication {

	public static void main(String[] args) {
		SpringApplication.run(FindAreaApplication.class, args);
	}

}
