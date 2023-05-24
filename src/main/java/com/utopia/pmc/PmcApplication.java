package com.utopia.pmc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PmcApplication {

	public static void main(String[] args) {
		SpringApplication.run(PmcApplication.class, args);
	}

}
