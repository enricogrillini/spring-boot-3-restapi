package it.eg.cookbook.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    GroupedOpenApi securityGroup() {
        return GroupedOpenApi.builder().group("security")
                .pathsToMatch("/api/v1/security/**").build();
    }

    @Bean
    GroupedOpenApi documentGroup() {
        return GroupedOpenApi.builder().group("document")
                .pathsToMatch("/api/v1/document/**").build();
    }
}
