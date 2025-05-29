package com.stockapp.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This class enables Swagger UI for automatic API documentation.
 */

@Configuration
public class SwaggerConfig {
	
	@Bean
	public OpenAPI apiInfo() {
		return new OpenAPI()
				.info(new Info()
						.title("Stock Portfolio Monitoring API")
						.description("Auto-generated documentation for all backend API's")
						.version("v1.0"));
		
	}

}
