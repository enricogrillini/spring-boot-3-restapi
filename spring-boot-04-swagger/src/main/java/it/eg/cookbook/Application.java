package it.eg.cookbook;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Document API", version = "${info.app.version}", description = "Rest API example: Document API"))
public class Application {
    public static void main (String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
