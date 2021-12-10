package br.com.magna.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//@EnableSpringDataWebSupport 
@EnableCaching
public class ApiApplication {
	
	//private static Logger logger = LoggerFactory.getLogger(ApiApplication.class);

	public static void main(String[] args) {
	//	logger.info("API INICIADA");
		SpringApplication.run(ApiApplication.class, args);
		//logger.info("API iniciada");
	}

}
