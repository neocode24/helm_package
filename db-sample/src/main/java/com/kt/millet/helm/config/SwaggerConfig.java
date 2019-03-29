package com.kt.millet.helm.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Slf4j
@Configuration
@Profile("!prd")
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2)
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.kt"))
                    .paths(PathSelectors.ant("/**"))
                    .build()
                    .apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo() {

        return new ApiInfo(
            "KT MSA 플랫폼 구축 프로젝트",
            "API Documents",
            "1.0",
            null,
            null,
            null,
            null,
            Collections.emptyList()
        );
    }
}
