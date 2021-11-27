package br.com.magna.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableSpringDataWebSupport
public class ApiApplication {
	
	private static Logger logger = LoggerFactory.getLogger(ApiApplication.class);

	public static void main(String[] args) {
		logger.info("Iniciando API");
		SpringApplication.run(ApiApplication.class, args);
		logger.info("API iniciada");
	}

}
