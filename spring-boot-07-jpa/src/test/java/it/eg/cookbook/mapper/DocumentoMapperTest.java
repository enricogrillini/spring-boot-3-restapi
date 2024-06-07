package it.eg.cookbook.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.eg.cookbook.model.Documento;
import it.eg.cookbook.model.entity.DocumentoEntity;
import it.eg.cookbook.model.mapper.DocumentoMapper;
import it.eg.cookbook.util.TestUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Stream;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("VERIFICA DocumentoMapper")
class DocumentoMapperTest {

    @Autowired
    protected DocumentoMapper jobEventMapper;

    @Autowired
    ObjectMapper objectMapper;

    @TestFactory
    @DisplayName("Check mapping Api to Entity")
    Stream<DynamicTest> mapping_Api_to_Entity() {
        File inputDir = new File("./src/test/resources/json/DocumentoMapperTest/api");

        return Arrays.stream(inputDir.listFiles())
                .filter(File::isFile)
                .map(dt -> DynamicTest.dynamicTest("Mapping: " + dt.getName(), () -> {
                    Documento documento = TestUtil.readObject("DocumentoMapperTest/entity/" + dt.getName(), Documento.class);
                    DocumentoEntity documentoEntity = jobEventMapper.apiToEntity(documento);

                    TestUtil.assertJsonEqualsFile("DocumentoMapperTest/entity/" + dt.getName(), objectMapper.writeValueAsString(documentoEntity));
                }));
    }

    @TestFactory
    Stream<DynamicTest> mapping_Entity_to_Api() {
        File inputDir = new File("./src/test/resources/json/DocumentoMapperTest/entity");

        return Arrays.stream(inputDir.listFiles())
                .filter(File::isFile)
                .map(dt -> DynamicTest.dynamicTest("Mapping: " + dt.getName(), () -> {
                    Documento documento = TestUtil.readObject("DocumentoMapperTest/api/" + dt.getName(), Documento.class);
                    DocumentoEntity documentoEntity = jobEventMapper.apiToEntity(documento);

                    TestUtil.assertJsonEqualsFile("DocumentoMapperTest/api/" + dt.getName(), objectMapper.writeValueAsString(documentoEntity));
                }));
    }


}
