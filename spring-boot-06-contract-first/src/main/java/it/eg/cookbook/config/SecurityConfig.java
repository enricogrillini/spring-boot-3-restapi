package it.eg.cookbook.config;

import it.eg.cookbook.filter.JwtAuthenticationTokenFilter;
import it.eg.cookbook.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    JwtService jwtService;

    private static final String[] WHITELIST = {
            // -- swagger ui
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/actuator/**",
    };

    public static final String DOCUMENT_URI = "/api/v1/document/**";
    public static final String SECURITY_URI = "/security/generate-token";

    public static final String RULE_READER = "READER";
    public static final String RULE_WRITER = "WRITER";
    public static final String RULE_ADMIN = "ADMIN";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(s ->
                        s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(a -> a
                        .requestMatchers(HttpMethod.GET, DOCUMENT_URI).permitAll() //.hasAnyAuthority(RULE_READER, RULE_WRITER, RULE_ADMIN)
                        .requestMatchers(HttpMethod.PUT, DOCUMENT_URI).hasAnyAuthority(RULE_WRITER, RULE_ADMIN)
                        .requestMatchers(HttpMethod.POST, DOCUMENT_URI).hasAnyAuthority(RULE_WRITER, RULE_ADMIN)
                        .requestMatchers(HttpMethod.DELETE, DOCUMENT_URI).hasAuthority(RULE_ADMIN)
                        .requestMatchers(HttpMethod.POST, SECURITY_URI).permitAll()
                        .requestMatchers(WHITELIST).permitAll()
                        //All
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthenticationTokenFilter(jwtService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}