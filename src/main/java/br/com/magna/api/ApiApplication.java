package br.com.magna.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableSpringDataWebSupport 
//@EnableSwagger2
public class ApiApplication {
	
	//private static Logger logger = LoggerFactory.getLogger(ApiApplication.class);

	public static void main(String[] args) {
	//	logger.info("API INICIADA");
		SpringApplication.run(ApiApplication.class, args);
		//logger.info("API iniciada");
	}

}
