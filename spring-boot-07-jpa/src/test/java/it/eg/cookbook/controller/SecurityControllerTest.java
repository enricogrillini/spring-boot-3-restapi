package it.eg.cookbook.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import it.eg.cookbook.model.Token;
import it.eg.cookbook.service.JwtService;
import it.eg.cookbook.util.TestUtil;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
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
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SecurityControllerTest {

    @Autowired
    JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private static final String URI = "/security/generate-token";

    @Test
    void postDocumentTestKOSec() throws Exception {
        String userStr = TestUtil.readFile("SecurityControllerTest/user.json");

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
