package it.eg.cookbook.actuator;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
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

@SpringBootTest
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ActuatorTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void get_Health() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/actuator/health")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        // Verifico lo stato della risposta
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    }

}