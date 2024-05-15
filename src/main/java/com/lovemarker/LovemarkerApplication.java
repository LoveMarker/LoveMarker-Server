package com.lovemarker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class LovemarkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LovemarkerApplication.class, args);
	}

}
