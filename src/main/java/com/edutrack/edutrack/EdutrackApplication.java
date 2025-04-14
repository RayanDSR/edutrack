package com.edutrack.edutrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.edutrack.model")
public class EdutrackApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdutrackApplication.class, args);
	}

}
