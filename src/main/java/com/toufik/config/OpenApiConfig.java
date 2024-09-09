package com.toufik.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

//@OpenAPIDefinition(
//        info = @Info(
//                title = "FSA News API",
//                version = "1.0",
//                description = "This is a simple News API, for fetching the latest news." +
//                        "It uses the News API to fetch the latest news.",
//                contact = @Contact(
//                        name = "Toufik",
//                        email = "toufik.saadane@gmail.com"
//                )
//        ),
//        servers = {
//                @Server(
//                        description = "local development server",
//                        url = "http://localhost:8080/swagger-ui/index.html#"
//                ),
//                @Server(
//                        description = "production server",
//                        url = "https://fsa-news-api.herokuapp.com/swagger-ui/index.html#"
//                )
//        }
//)
@SecurityScheme(
        type = SecuritySchemeType.HTTP,
        name = "BearerAuth",
        description = "This is a bearer token for authentication",
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.DEFAULT
)
public class OpenApiConfig {
}
