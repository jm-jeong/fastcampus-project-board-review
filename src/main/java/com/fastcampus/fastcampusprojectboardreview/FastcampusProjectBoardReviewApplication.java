package com.fastcampus.fastcampusprojectboardreview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan//ThymeleafConfig setting
@SpringBootApplication
public class FastcampusProjectBoardReviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(FastcampusProjectBoardReviewApplication.class, args);
	}

}
