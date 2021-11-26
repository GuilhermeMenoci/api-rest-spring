package br.com.magna.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

//	private static final String BASE_PACKAGE = "br.com.magna.magna";
//	private static final String API_TITLE = "API Rest ";
//	private static final String API_DESCRIPTION = "REST API Magna";
//	private static final String CONTACT_NAME = "Guilherme Menoci";
//	private static final String CONTACT_GITHUB = "https://github.com/GuilhermeMenoci";
//	private static final String CONTACT_EMAIL = "gmenoci@magnasistemas.com";

	
	@Bean
	public Docket forumApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("br.com.magna")).paths(PathSelectors.ant("/**")).build();
	}
	
//	  private ApiInfo buildApiInfo() {
//	        return new ApiInfoBuilder()
//	                .title(API_TITLE)
//	                .description(API_DESCRIPTION)
//	                .version("1.0.0")
//	                .contact(new Contact(CONTACT_NAME, CONTACT_GITHUB, CONTACT_EMAIL))
//	                .build();
//	    }
}
