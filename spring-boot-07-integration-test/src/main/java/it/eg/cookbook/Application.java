package it.eg.cookbook;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;


@OpenAPIDefinition(info = @Info(title = "Document API", version = "${info.app.version}", description = "Rest API example: Document API"))
@SecurityScheme(name = "bearerAuth", scheme = "bearer", bearerFormat = "JWT", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
public class Application {
    public static void main (String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
