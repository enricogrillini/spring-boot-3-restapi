package it.eg.cookbook.controller;


import it.eg.cookbook.model.Token;
import it.eg.cookbook.model.User;
import it.eg.cookbook.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController implements SecurityApi {

    @Autowired
    private JwtService jwtService;

    @Override
    public ResponseEntity<Token> postGenerateToken(User user) {
        Token token = Token.builder()
                .jwtToken(jwtService.createJWT(user))
                .build();

        return ResponseEntity.ok(token);
    }
}

