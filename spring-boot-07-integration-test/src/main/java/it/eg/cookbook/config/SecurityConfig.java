package it.eg.cookbook.config;

import it.eg.cookbook.filter.JwtAuthenticationTokenFilter;
import it.eg.cookbook.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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

    public static final String BASE_URI = "/api/v1/document/**";

    public static final String RULE_READER = "READER";
    public static final String RULE_WRITER = "WRITER";
    public static final String RULE_ADMIN = "ADMIN";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, BASE_URI).permitAll() //.hasAnyAuthority(RULE_READER, RULE_WRITER, RULE_ADMIN)
                .antMatchers(HttpMethod.PUT, BASE_URI).hasAnyAuthority(RULE_WRITER, RULE_ADMIN)
                .antMatchers(HttpMethod.POST, BASE_URI).hasAnyAuthority(RULE_WRITER, RULE_ADMIN)
                .antMatchers(HttpMethod.DELETE, BASE_URI).hasAuthority(RULE_ADMIN)
                .antMatchers(WHITELIST).permitAll()
                .antMatchers("/api/v1/security/generate-token").permitAll()
                //All
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationTokenFilter(jwtService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}