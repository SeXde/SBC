package com.urjc.alumno.alvaro.sbc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbcApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenApi() {
		return new OpenAPI().info(
				new Info()
						.title("SBC API")
						.title("Search and expand nodes with SBC API")
						.description("This API is used for fetching nodes from desired endpoints")
		);
	}

}
