package com.mentee.microservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "Mentee Rest Service",
                description = "REST Service to handle mentee related functionality",
                version = "1.0"
        )

)
@SpringBootApplication
public class MenteeServiceAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(MenteeServiceAppApplication.class, args);
    }

}
