package it.eg.cookbook.config;

import it.eg.cookbook.model.JobStatus;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
// NOTA: Questo component è necessario per poter utilizzare gli ENUM come @PathVariable
public class ApiConverter implements WebMvcConfigurer {

    interface EnvironmentNameConverter extends Converter<String, JobStatus> {
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(String.class, JobStatus.class, JobStatus::fromValue);
    }

}