package it.eg.cookbook.controller;

import it.eg.cookbook.service.DocumentoService;
import it.eg.cookbook.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
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
class DocumentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DocumentoService documentoService;

    private static final String URI = "/documento";
    private static final String URI_ID = "/documento/{id}";

    @BeforeEach
    void init() {
        documentoService.afterPropertiesSet();
    }

    @Test
    void create() throws Exception {
        // Act
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.post(URI)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(TestUtil.readFile("DocumentoControllerTest/mock/Document_new.json")))
                .andReturn();

        // Verify
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        TestUtil.assertJsonEqualsFile("DocumentoControllerTest/expected/create.json", mvcResult.getResponse(), "id");
    }

    @Test
    void delete() throws Exception {
        // Act
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .delete(URI_ID, 1)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        // Verify
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        TestUtil.assertJsonEqualsFile("DocumentoControllerTest/expected/delete.json", mvcResult.getResponse());
    }


    @Test
    void delete_notFound_KO() throws Exception {
        // Act
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .delete(URI_ID, 100L)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        // Verify
        assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
        TestUtil.assertJsonEqualsFile("DocumentoControllerTest/expected/delete_notFound_KO.json", mvcResult.getResponse());
    }

    @Test
    void find() throws Exception {
        // Act
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .get(URI)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        // Verify
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        TestUtil.assertJsonEqualsFile("DocumentoControllerTest/expected/find.json", mvcResult.getResponse());
    }


    @Test
    void get() throws Exception {
        // Act
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .get(URI_ID, 1)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        // Verify
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        TestUtil.assertJsonEqualsFile("DocumentoControllerTest/expected/get.json", mvcResult.getResponse());
    }

    @Test
    void get_notFound_KO() throws Exception {
        // Act
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .get(URI_ID, 100)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        // Verify
        assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
        TestUtil.assertJsonEqualsFile("DocumentoControllerTest/expected/get_notFound_KO.json", mvcResult.getResponse());
    }

    @Test
    void update() throws Exception {
        // Act
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .put(URI_ID, 2L)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(TestUtil.readFile("DocumentoControllerTest/mock/Document_2.json")))
                .andReturn();

        // Verify
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        TestUtil.assertJsonEqualsFile("DocumentoControllerTest/expected/update.json", mvcResult.getResponse());
    }

    @Test
    void update_notFound_KO() throws Exception {
        // Act
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .put(URI_ID, 100L)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(TestUtil.readFile("DocumentoControllerTest/mock/Document_100.json")))
                .andReturn();

        // Verify
        assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
        TestUtil.assertJsonEqualsFile("DocumentoControllerTest/expected/update_notFound_KO.json", mvcResult.getResponse());
    }

}