package it.eg.cookbook.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import it.eg.cookbook.config.SecurityConfig;
import it.eg.cookbook.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private JwtService jwtService;

    public JwtAuthenticationTokenFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority(SecurityConfig.RULE_ADMIN));

        return new UsernamePasswordAuthenticationToken("admin", null, list);

//        String token = request.getHeader(JwtService.TOKEN_HEADER);
//        if (token != null && token.startsWith(JwtService.TOKEN_PREFIX)) {
//            try {
//                Jws<Claims> parsedToken = jwtService.parseToken(token);
//
//                Collection<? extends GrantedAuthority> authorities = getAuthorities(parsedToken);
//                String username = parsedToken.getBody().getSubject();
//                if (username != null && !username.isEmpty()) {
//                    return new UsernamePasswordAuthenticationToken(username, null, authorities);
//                }
//            } catch (Exception e) {
//                return null;
//            }
//        }
//        return null;
    }

    // Ritorna la lista delle GrantedAuthority - Sarà da implementare in funzione del contesto applicativo ad es. con chiamate LDAP
    private Collection<? extends GrantedAuthority> getAuthorities(Jws<Claims> parsedToken) {
        List<SimpleGrantedAuthority> list = new ArrayList<>();

        String username = parsedToken.getBody().getSubject().toLowerCase();
        if (username.startsWith("reader")) {
            list.add(new SimpleGrantedAuthority(SecurityConfig.RULE_READER));
        } else if (username.startsWith("writer")) {
            list.add(new SimpleGrantedAuthority(SecurityConfig.RULE_WRITER));
        } else if (username.startsWith("admin")) {
            list.add(new SimpleGrantedAuthority(SecurityConfig.RULE_ADMIN));
        }

        return list;
    }


}
