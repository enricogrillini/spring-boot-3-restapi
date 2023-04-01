package it.eg.cookbook;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.eg.cookbook.error.ResponseCode;
import it.eg.cookbook.model.Document;
import it.eg.cookbook.model.ResponseMessage;
import it.eg.cookbook.model.User;
import it.eg.cookbook.service.JwtService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DocumentControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    private static final String URI = "/api/v1/document";
    private static final String URI_ID = "/api/v1/document/{documentId}";

    private Document mockDocument(Integer id) {
        return Document.builder()
                .id(id)
                .name("Documento " + id)
                .description("Descrizione Documento " + id)
                .data(LocalDate.now())
                .updateBy("ugo")
                .build();
    }

    private String mockToken(String user) {
        return jwtService.createJWT(User.builder()
                .issuer("www.idm.com")
                .subject(user)
                .audience("progetto-cookbook")
                .customClaim("customClaim")
                .ttlMillis(Long.valueOf(3600 * 1000))
                .build());
    }


    @Test
    @Order(1)
    void getDocumentsTest() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .get(URI)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + mockToken("reader-1")))
                .andReturn();

        // Verifico lo stato della risposta
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());

        // Verifico che la lista di documenti non sia vuota
        Document[] documents = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Document[].class);
        assertEquals(3, documents.length);
    }

    @Test
    @Order(2)
    void getDocumentTest() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .get(URI_ID, 1)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + mockToken("reader-1")))
                .andReturn();

        // Verifico lo stato della risposta
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());

        // Verifico che lo Documento sia corretto
        Document documento = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Document.class);
        assertEquals(1, documento.getId());
    }

    @Test
    @Order(3)
    void getDocumentTestKO() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .get(URI_ID, 100)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + mockToken("reader-1")))
                .andReturn();

        // Verifico lo stato della risposta
        assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());

        // Verifico che lo Documento sia corretto
        ResponseMessage responseMessage = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ResponseMessage.class);
        assertEquals(ResponseCode.NOT_FOUND.toString(), responseMessage.getCode());
        assertEquals(ResponseCode.NOT_FOUND.getDescription(), responseMessage.getDescription());
        assertEquals("Documento non trovato", responseMessage.getDetail());
    }

    @Test
    @Order(4)
    void deleteDocumentTest() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .delete(URI_ID, 1)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + mockToken("admin-2")))
                .andReturn();

        // Verifico lo stato della risposta
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());

        // Verifico che lo Documento sia corretto
        ResponseMessage responseMessage = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ResponseMessage.class);

        assertEquals(ResponseCode.OK.toString(), responseMessage.getCode());
        assertEquals(ResponseCode.OK.getDescription(), responseMessage.getDescription());
        assertEquals("Documento eliminato correttamente", responseMessage.getDetail());
    }

    @Test
    @Order(5)
    void deleteDocumentTestKO() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .delete(URI_ID, 100)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + mockToken("admin-2")))
                .andReturn();

        // Verifico lo stato della risposta
        assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());

        // Verifico che il Documento sia corretto
        ResponseMessage responseMessage = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ResponseMessage.class);
        assertEquals(ResponseCode.NOT_FOUND.toString(), responseMessage.getCode());
        assertEquals(ResponseCode.NOT_FOUND.getDescription(), responseMessage.getDescription());
        assertEquals("Documento non trovato", responseMessage.getDetail());
    }

    @Test
    @Order(6)
    void deleteDocumentTestKOSec() throws Exception {
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
    @Order(7)
    void postDocumentTest() throws Exception {
        String documentStr = objectMapper.writeValueAsString(mockDocument(5));
        String jwtToken = jwtService.createJWT(User.builder().issuer("www.idm.com").subject("writer-2").audience("progetto-cookbook").customClaim("customClaim").ttlMillis(Long.valueOf(3600 * 1000)).build());

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.post(URI)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(documentStr)
                        .header("Authorization", "Bearer " + jwtToken))
                .andReturn();

        // Verifico lo stato della risposta
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());

        // Verifico che lo Documento sia corretto
        ResponseMessage responseMessage = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ResponseMessage.class);
        assertEquals(ResponseCode.OK.toString(), responseMessage.getCode());
        assertEquals(ResponseCode.OK.getDescription(), responseMessage.getDescription());
        assertEquals("Documento inserito correttamente", responseMessage.getDetail());
    }

    @Test
    @Order(8)
    void postDocumentTestKO() throws Exception {
        String documentStr = objectMapper.writeValueAsString(mockDocument(5));

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.post(URI)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(documentStr)
                        .header("Authorization", "Bearer " + mockToken("writer-2")))
                .andReturn();

        // Verifico lo stato della risposta
        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());

        // Verifico che lo Documento sia corretto
        ResponseMessage responseMessage = objectMapper.readValue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8), ResponseMessage.class);
        assertEquals(ResponseCode.BUSINESS_ERROR.toString(), responseMessage.getCode());
        assertEquals(ResponseCode.BUSINESS_ERROR.getDescription(), responseMessage.getDescription());
        assertEquals("Documento già presente", responseMessage.getDetail());
    }


    @Test
    @Order(9)
    void postDocumentTestKOSec() throws Exception {
        String documentStr = objectMapper.writeValueAsString(mockDocument(2));

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .post(URI)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(documentStr)
                        .header("Authorization", "Bearer fake"))
                .andReturn();

        // Verifico lo stato della risposta
        assertEquals(HttpStatus.FORBIDDEN.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    @Order(10)
    void putDocumentTest() throws Exception {
        String documentStr = objectMapper.writeValueAsString(mockDocument(2));

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .put(URI)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(documentStr)
                        .header("Authorization", "Bearer " + mockToken("writer-2")))
                .andReturn();

        // Verifico lo stato della risposta
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());

        // Verifico che lo Documento sia corretto
        ResponseMessage responseMessage = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ResponseMessage.class);
        assertEquals(ResponseCode.OK.toString(), responseMessage.getCode());
        assertEquals(ResponseCode.OK.getDescription(), responseMessage.getDescription());
        assertEquals("Documento aggiornato correttamente", responseMessage.getDetail());
    }

    @Test
    @Order(11)
    void putDocumentTestKO() throws Exception {
        String documentStr = objectMapper.writeValueAsString(mockDocument(6));

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .put(URI)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(documentStr)
                        .header("Authorization", "Bearer " + mockToken("writer-2")))
                .andReturn();

        // Verifico lo stato della risposta
        assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());

        // Verifico che lo Documento sia corretto
        ResponseMessage responseMessage = objectMapper.readValue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8), ResponseMessage.class);
        assertEquals(ResponseCode.NOT_FOUND.toString(), responseMessage.getCode());
        assertEquals(ResponseCode.NOT_FOUND.getDescription(), responseMessage.getDescription());
        assertEquals("Documento non trovato", responseMessage.getDetail());
    }

    @Test
    @Order(12)
    void putDocumentTestKOSec() throws Exception {
        String documentStr = objectMapper.writeValueAsString(mockDocument(6));

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .put(URI)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(documentStr))
                .andReturn();

        // Verifico lo stato della risposta
        assertEquals(HttpStatus.FORBIDDEN.value(), mvcResult.getResponse().getStatus());
    }

}