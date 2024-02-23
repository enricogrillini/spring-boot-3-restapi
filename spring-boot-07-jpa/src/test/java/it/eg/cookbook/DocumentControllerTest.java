package it.eg.cookbook;

import it.eg.cookbook.error.ResponseCode;
import it.eg.cookbook.model.Documento;
import it.eg.cookbook.model.Message;
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
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class DocumentControllerTest extends AbstractTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    private static final String URI = "/documento";
    private static final String URI_ID = "/documento/{id}";

    private Documento mockDocument(Long id) {
        return new Documento()
                .id(id)
                .nome("Documento " + id)
                .descrizione("Descrizione Documento " + id)
                .data(LocalDate.now())
                .updateBy("ugo");
    }

    private String mockToken(String user) {
        return jwtService.createJWT(new User()
                .issuer("www.idm.com")
                .subject(user)
                .audience("progetto-cookbook")
                .customClaim("customClaim")
                .ttlMillis(Long.valueOf(3600 * 1000)));
    }

    @Test
    void create() throws Exception {
        // Act
        String documentStr = objectMapper.writeValueAsString(mockDocument(null));
        String jwtToken = jwtService.createJWT(new User().issuer("www.idm.com").subject("writer-2").audience("progetto-cookbook").customClaim("customClaim").ttlMillis(Long.valueOf(3600 * 1000)));

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.post(URI)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(documentStr)
                        .header("Authorization", "Bearer " + jwtToken))
                .andReturn();

        // Verify
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertJsonEqualsFile("expected/create.json", mvcResult.getResponse().getContentAsString(), "id");
    }

    @Test
    void create_security_KO() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .post(URI)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(readFile("newDocument.json"))
                        .header("Authorization", "Bearer fake"))
                .andReturn();

        // Verifico lo stato della risposta
        assertEquals(HttpStatus.FORBIDDEN.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    void find() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .get(URI)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + mockToken("reader-1")))
                .andReturn();

        // Verifico lo stato della risposta
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());

        // Verifico che la lista di documenti non sia vuota
        Documento[] documents = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Documento[].class);
        assertEquals(3, documents.length);
    }

    @Test
    void delete() throws Exception {
        // Act
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .delete(URI_ID, 1)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + mockToken("admin-2")))
                .andReturn();

        // Verify
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertJsonEqualsFile("expected/delete.json", mvcResult.getResponse().getContentAsString());
    }


    @Test
    void delete_notFound_KO() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .delete(URI_ID, 100L)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + mockToken("admin-2")))
                .andReturn();

        // Verifico lo stato della risposta
        assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());

        // Verifico che il Documento sia corretto
        Message responseMessage = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Message.class);
        assertEquals(ResponseCode.NOT_FOUND.toString(), responseMessage.getCode());
        assertEquals(ResponseCode.NOT_FOUND.getDescription(), responseMessage.getDescription());
        assertEquals("Documento non trovato", responseMessage.getDetail());
    }

    @Test
    void delete_security_KO() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .delete(URI_ID, "XX")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer fake"))
                .andReturn();

        // Verifico lo stato della risposta
        assertEquals(HttpStatus.FORBIDDEN.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    void get() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .get(URI_ID, 1)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + mockToken("reader-1")))
                .andReturn();

        // Verifico lo stato della risposta
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());

        // Verifico che lo Documento sia corretto
        Documento documento = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Documento.class);
        assertEquals(1, documento.getId());
    }

    @Test
    void get_KO() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .get(URI_ID, 100)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + mockToken("reader-1")))
                .andReturn();

        // Verifico lo stato della risposta
        assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());

        // Verifico che lo Documento sia corretto
        Message responseMessage = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Message.class);
        assertEquals(ResponseCode.NOT_FOUND.toString(), responseMessage.getCode());
        assertEquals(ResponseCode.NOT_FOUND.getDescription(), responseMessage.getDescription());
        assertEquals("Documento non trovato", responseMessage.getDetail());
    }


    @Test
    void update() throws Exception {
        String documentStr = objectMapper.writeValueAsString(mockDocument(2L));

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .put(URI_ID, 2L)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(documentStr)
                        .header("Authorization", "Bearer " + mockToken("writer-2")))
                .andReturn();

        // Verifico lo stato della risposta
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());

        // Verifico che lo Documento sia corretto
        Documento documento = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Documento.class);
        assertNotNull(documento);
    }

    @Test
    void update_notFound_KO() throws Exception {
        String documentStr = objectMapper.writeValueAsString(mockDocument(100L));

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .put(URI_ID, 100L)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(documentStr)
                        .header("Authorization", "Bearer " + mockToken("writer-2")))
                .andReturn();

        // Verifico lo stato della risposta
        assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());

        // Verifico che lo Documento sia corretto
        Message responseMessage = objectMapper.readValue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8), Message.class);
        assertEquals(ResponseCode.NOT_FOUND.toString(), responseMessage.getCode());
        assertEquals(ResponseCode.NOT_FOUND.getDescription(), responseMessage.getDescription());
        assertEquals("Documento non trovato", responseMessage.getDetail());
    }

    @Test
    void update_security_KO() throws Exception {
        String documentStr = objectMapper.writeValueAsString(mockDocument(100L));

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .put(URI_ID, 100L)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(documentStr))
                .andReturn();

        // Verifico lo stato della risposta
        assertEquals(HttpStatus.FORBIDDEN.value(), mvcResult.getResponse().getStatus());
    }

}