package com.techblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.models.Contact;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
    public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(metaData())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.techblog.controllers")).build();
		  
    }
	
    private ApiInfo metaData() {
    	
        ApiInfo apiInfo = new ApiInfo(
                "TECHBLOG",
                "REST API'S",
                "1.0",
                "Terms of service",
                new springfox.documentation.service.Contact("Rizwan", "", "rizwan8800@gmail.com"),
               null,null);
        return apiInfo;
    }

}
