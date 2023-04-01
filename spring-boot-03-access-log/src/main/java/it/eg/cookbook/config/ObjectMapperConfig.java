package it.eg.cookbook.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

@Configuration
public class ObjectMapperConfig {

    @Bean
    ObjectMapper objectMapper() {
        return JsonMapper
                .builder()
                .addModules(new Jdk8Module(), new JavaTimeModule(), new ParameterNamesModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, false)
                .enable(SerializationFeature.INDENT_OUTPUT)
                .serializationInclusion(JsonInclude.Include.NON_ABSENT)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .defaultTimeZone(TimeZone.getDefault())
                .build();
    }

}
