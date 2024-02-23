package it.eg.cookbook;

import it.eg.cookbook.model.DocumentPojo;
import org.junit.Ignore;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@Commit
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public abstract class DocumentControllerAbstract extends AbstractTest {

    private static final String URI = "http://localhost:8082/api/v1/document";
    private static final String URI_ID = "http://localhost:8082/api/v1/document/{documentId}";

    @Test
    @Order(1)
    void getDocuments() {
        doRestTest(URI, HttpMethod.GET, "", null, HttpStatus.OK);
    }

    @Test
    @Order(2)
    void getDocument() {
        doRestTest(URI_ID, HttpMethod.GET, "", null, HttpStatus.OK, 1);
    }

    @Test
    @Order(3)
    void getDocumentKO() {
        doRestTest(URI_ID, HttpMethod.GET, "", null, HttpStatus.NOT_FOUND, 100);
    }

    @Test
    @Order(4)
    void deleteDocument() {
        assertEquals(1, jdbcTemplate.queryForObject("Select count(*) from document where Id = 1", Integer.class));

        doRestTest(URI_ID, HttpMethod.DELETE, "", null, HttpStatus.OK, 1);

        assertEquals(0, jdbcTemplate.queryForObject("Select count(*) from document where Id = 1", Integer.class));
    }

    @Test
    @Order(5)
    void deleteDocumentKO() throws Exception {
        assertEquals(0, jdbcTemplate.queryForObject("Select count(*) from document where Id = 100", Integer.class));

        doRestTest(URI_ID, HttpMethod.DELETE, "", null, HttpStatus.NOT_FOUND, 100);
    }


//    @Test
//    @Order(6)
//    void deleteDocumentTestKOSec() throws Exception {
//        MvcResult mvcResult = mockMvc
//                .perform(MockMvcRequestBuilders
//                        .delete(URI_ID, "XX")
//                        .accept(MediaType.APPLICATION_JSON_VALUE)
//                        .header("Authorization", "Bearer fake"))
//                .andReturn();
//
//        // Verifico lo stato della risposta
//        assertEquals(HttpStatus.FORBIDDEN.value(), mvcResult.getResponse().getStatus());
//    }


    @Test
    @Order(7)
    @Disabled
    void postDocument() throws Exception {

        assertEquals(0, jdbcTemplate.queryForObject("Select count(*) from document where name = 'doc-5'", Integer.class));

      //  doRestTest(URI, HttpMethod.POST, "", readRequestFile(), HttpStatus.OK);

        DocumentPojo documentPojo = jdbcTemplate.queryForObject("Select * from document where name = 'doc-5'", new BeanPropertyRowMapper<>(DocumentPojo.class));
        assertJsonEquals(readExpectedFile("-pojo"), objectMapper.writeValueAsString(documentPojo));
    }

    @Test
    @Order(8)
    void postDocumentKO() throws Exception {
       // doRestTest(URI, HttpMethod.POST, "", readRequestFile(), HttpStatus.BAD_REQUEST);
    }

//    @Test
//    @Order(9)
//    void postDocumentTestKOSec() throws Exception {
//        String documentStr = objectMapper.writeValueAsString(mockDocument(2));
//
//        MvcResult mvcResult = mockMvc
//                .perform(MockMvcRequestBuilders
//                        .post(URI)
//                        .accept(MediaType.APPLICATION_JSON_VALUE)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(documentStr)
//                        .header("Authorization", "Bearer fake"))
//                .andReturn();
//
//        // Verifico lo stato della risposta
//        assertEquals(HttpStatus.FORBIDDEN.value(), mvcResult.getResponse().getStatus());
//    }


}


