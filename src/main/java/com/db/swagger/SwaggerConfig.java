package com.db.swagger;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class SwaggerConfig {

    private final String openApiPath = "api-docs.json";

    @Bean
    public OpenAPI openAPI() throws IOException {
        InputStream openApiJSON = getClass().getClassLoader().getResourceAsStream(openApiPath);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(openApiJSON, OpenAPI.class);
    }

//    @Bean
//    public OpenAPI apiInfo() {
//        return new OpenAPI()
//            .info(
//                new Info()
//                    .title("Swagger API")
//                    .description("API desenvolvida para passar conhecimento sobre as tecnologias que vão ser usadas no projeto DB Rank")
//                    .contact(new Contact()
//                        .name("DB")
//                        .email("info@dbserver.com.br")
//                        .url("https://db.tec.br/")
//                    )
//                    .version("1.0.0")
//                    .license(new License())
//                    .termsOfService("")
//            );
//    }
}
