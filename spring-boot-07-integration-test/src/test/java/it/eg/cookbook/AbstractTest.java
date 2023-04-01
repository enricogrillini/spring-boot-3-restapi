package it.eg.cookbook;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.eg.cookbook.config.ObjectMapperConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.shaded.org.apache.commons.io.FileUtils;
import org.testcontainers.shaded.org.apache.commons.io.comparator.NameFileComparator;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.time.Duration;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Classe comune per la gestione di Unit e Integration test.
 * Nota:
 * - gli Unit Test attivano il contesto spring per cui tutte le annotazioni Spring (ad es. @Value, @Autowired, etc.) sono attive
 * - gli Intetìgration Test NON attivano il contesto spring: l'applicazione gira su container
 */
@Slf4j
public abstract class AbstractTest {

    private TestInfo testInfo;

    private static boolean firstTest = true;
    protected static ObjectMapper objectMapper;
    protected static RestTemplate restTemplate;
    protected static JdbcTemplate jdbcTemplate;

    // In caso di Unit test il Data Source è impostato da Spring
    @Autowired
    protected DataSource dataSource;

    // In caso di Integration test il Data Source deve essere inizializzato
    private static final DataSource IT_DATA_SOURCE =
            new DriverManagerDataSource("jdbc:postgresql://localhost:5432/postgres?currentSchema=cookbook", "cookbook", "cookbook");

    @BeforeEach
    void setup(TestInfo testInfo) throws SQLException {
        this.testInfo = testInfo;

        // Operazioni da eseguire allo startup
        if (firstTest) {
            // Integration test
            if (isIntegrationTest()) {
                // Avvio il compose
                startCompose();

                // Imposto il datasource
                dataSource = IT_DATA_SOURCE;
            }

            if (isUnitTest()) {
                initDB();
            }

            objectMapper = ObjectMapperConfig.defaultObjectMapper();
            restTemplate = new RestTemplate();
            jdbcTemplate = new JdbcTemplate(dataSource);

            firstTest = false;
        }

        // Operazioni da eseguire ad ogni test
    }

    public boolean isIntegrationTest() {
        return testInfo.getTestClass().get().getSimpleName().endsWith("IT");
    }

    public boolean isUnitTest() {
        return !isIntegrationTest();
    }

    public String getTestClass() {
        return testInfo.getTestClass().get().getSimpleName();
    }

    public String getTestClassBaseName() {
        String className = getTestClass();

        return className.endsWith("IT") ? className.substring(0, className.length() - 2) : className.substring(0, className.length() - 4);
    }

    public String getTestMethod() {
        return testInfo.getTestMethod().get().getName();
    }

    private void initDB() throws SQLException {
        File[] sqlFiles = new File("src/test/resources/sql").listFiles((dir, name) -> name.endsWith(".sql"));

        Arrays.sort(sqlFiles, NameFileComparator.NAME_COMPARATOR);
        try (Connection con = dataSource.getConnection()) {
            for (File f : sqlFiles) {
                log.info("Loading SQL script {} into database", f.getName());
                ScriptUtils.executeSqlScript(con, new FileSystemResource(f));
            }
        }

    }

    private void startCompose() {
        // Singleton container:
        // https://www.testcontainers.org/test_framework_integration/manual_lifecycle_control/
        //
        // 16/09/2022: --compatibility affinché Testcontainers riconosca
        // l'avvio dei servizi anche su versioni recenti di docker-compose
        // che userebbero il separatore "-" anziché "_" nei nomi container:
        // https://techhelpnotes.com/docker-compose-container-name-use-dash-instead-of-underscore-_-2/
        // Nessun problema su Windows con docker-compose 1.29.2 bensì su:
        // - Linux: docker-compose 1.26.0 & Docker Compose v2.6.0
        // - macOS: Docker Compose v2.10.2
        new DockerComposeContainer<>(new File("docker/docker-compose.yml"))
                .withOptions("--compatibility")
                .withExposedService("postgres", 5432, Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(120)))
                .withExposedService("rest-api", 8082, Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(120)))
                .withBuild(true)
                .withLocalCompose(true)
                .start();
    }

    protected String readMockFile() {
        return readMockFile("");
    }

    protected String readMockFile(String fileNameSuffix) {
        return readFile("/mock/", fileNameSuffix);
    }

    protected <T> T readMockFile(Class<T> objectClass) throws JsonProcessingException {
        return readMockFile(objectClass, "");
    }

    protected <T> T readMockFile(Class<T> objectClass, String fileNameSuffix) throws JsonProcessingException {
        return objectMapper.readerFor(objectClass).readValue(readMockFile(fileNameSuffix));
    }

    protected String readExpectedFile() {
        return readExpectedFile("");
    }

    protected String readExpectedFile(String fileNameSuffix) {
        return readFile("/expected/", fileNameSuffix);
    }

//    protected String readRequestFile() {
//        return readRequestFile("");
//    }
//
//    protected String readRequestFile(String fileNameSuffix) {
//        return readFile("/request/", fileNameSuffix);
//    }
//
//    protected String readModelFile() {
//        return readModelFile("");
//    }
//
//    protected String readModelFile(String fileNameSuffix) {
//        return readFile("/model/", fileNameSuffix);
//    }

    public String readFile(String type, String fileNameSuffix) {
        String fileNamePath = "src/test/resources/json/{0}{1}{2}{3}.json";
        File file = new File(MessageFormat.format(fileNamePath, getTestClassBaseName(), type, getTestMethod(), fileNameSuffix));

        return readFile(file);
    }

    public static String readFile(File readFile) {
        if (!readFile.isFile()) {
            Assertions.fail("File " + readFile + " non leggibile");
        }

        if (!readFile.exists()) {
            Assertions.fail("File " + readFile + " non trovato");
        }

        try {
            return FileUtils.readFileToString(readFile, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
            Assertions.fail("File " + readFile + " non leggibile", ex);
            return null;
        }
    }

    void assertJsonEquals(String expectedStr, String actualStr) {
        try {
            try {
                // "STRICT" pro fallimento test in presenza di campi aggiuntivi
                JSONAssert.assertEquals(expectedStr, actualStr, JSONCompareMode.STRICT);
            } catch (AssertionError ex) {
                String fileNamePath = "./target/actual/{0}/{1}.json";
                String className = getTestClass();
                String methodName = getTestMethod();

                FileUtils.writeStringToFile(new File(MessageFormat.format(fileNamePath, className, methodName)), actualStr, StandardCharsets.UTF_8);
                fail(ex);
            }
        } catch (JSONException | IOException ex) {
            log.error(ex.getMessage(), ex);
            fail(ex);
        }
    }


    void doRestTest(String url, HttpMethod httpMethod, String authorization, Object payload, HttpStatus expectedStatus, Object... uriVariables) throws RestClientException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        if (!StringUtils.isEmpty(authorization)) {
            headers.add(HttpHeaders.AUTHORIZATION, authorization);
        }

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, httpMethod, new HttpEntity<>(payload, headers), String.class, uriVariables);
            assertEquals(expectedStatus, response.getStatusCode());
            assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
            assertJsonEquals(readExpectedFile(), response.getBody());
        } catch (HttpStatusCodeException e) {
            assertEquals(expectedStatus, e.getStatusCode());
            assertEquals(MediaType.APPLICATION_JSON, e.getResponseHeaders().getContentType());
            assertJsonEquals(readExpectedFile(), e.getResponseBodyAsString());
        }
    }

}
