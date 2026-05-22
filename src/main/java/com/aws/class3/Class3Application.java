package com.aws.class3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class Class3Application {

	public static void main(String[] args) {
		SpringApplication.run(Class3Application.class, args);
	}

}
