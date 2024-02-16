package com.ssafy.cadang.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration//@EnableWebMvc -> springdoc 2.3.0버전일 때 활성화
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {

        Info info = new Info()
                .title("CADANG")
                .version("v1.0.0") //처음에 0.0.1이었음 (딱히상관없을듯)
                .description("인생 콰당 방지 서비스");

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}

