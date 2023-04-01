package it.eg.cookbook;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import it.eg.cookbook.model.Token;
import it.eg.cookbook.model.User;
import it.eg.cookbook.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityTestaaa {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    JwtService jwtService;

    @Autowired
    private MockMvc mockMvc;

    private static final String URI = "/api/v1/security/generate-token";

    @Test
    void postDocumentTestKOSec() throws Exception {
        String userStr = objectMapper.writeValueAsString(new User()
                .issuer("www.idm.com")
                .subject("reader")
                .audience("progetto-cookbook")
                .customClaim("customClaim")
                .ttlMillis(Long.valueOf(3600 * 1000)));

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .post(URI)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(userStr))
                .andReturn();

        // Verifico lo stato della risposta
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());

        // Verifico la risposta
        Token token = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Token.class);
        assertNotNull(token.getJwtToken());

        Jws<Claims> claims = jwtService.parseToken(token.getJwtToken());
        assertEquals("www.idm.com", claims.getBody().getIssuer());
        assertEquals("reader", claims.getBody().getSubject());
        assertEquals("progetto-cookbook", claims.getBody().getAudience());
        assertEquals("customClaim", claims.getBody().get("customClaim"));
    }

}
