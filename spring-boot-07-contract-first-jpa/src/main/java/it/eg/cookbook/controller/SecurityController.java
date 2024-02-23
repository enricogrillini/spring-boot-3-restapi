package it.eg.cookbook.controller;

import it.eg.cookbook.model.Token;
import it.eg.cookbook.model.User;
import it.eg.cookbook.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityController implements SecurityApi {

    private final JwtService jwtService;

    @Override
    public ResponseEntity<Token> postGenerateToken(User user) {
        Token token = new Token().jwtToken(jwtService.createJWT(user));

        return ResponseEntity.ok(token);
    }
}

