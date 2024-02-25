package it.eg.cookbook.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    GroupedOpenApi securityGroup() {
        return GroupedOpenApi.builder().group("security")
                .pathsToMatch("/security/**").build();
    }

    @Bean
    GroupedOpenApi documentGroup() {
        return GroupedOpenApi.builder().group("documento")
                .pathsToMatch("/documento/**").build();
    }
}
