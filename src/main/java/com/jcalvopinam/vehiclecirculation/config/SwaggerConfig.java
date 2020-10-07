package com.jcalvopinam.vehiclecirculation.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.HashSet;

/**
 * This class configures swagger for API documentation.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static Contact getContactInfo() {
        return new Contact("Juan Calvopina", "https://jcalvopinam.github.io", "juan.calvopina@gmail.com");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Vehicle Circulation Service - \"Pico y Placa\" predictor")
                                   .description("API Documentation")
                                   .version("1.0")
                                   .contact(getContactInfo())
                                   .build();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                                                      .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                                                      .paths(PathSelectors.any())
                                                      .build()
                                                      .pathMapping("/")
                                                      .enableUrlTemplating(true)
                                                      .apiInfo(apiInfo())
                                                      .produces(new HashSet<>(Collections.singletonList(
                                                              MediaType.APPLICATION_JSON_VALUE)));
    }

}