package it.eg.cookbook;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.fail;

@Slf4j
public abstract class AbstractTest {

    private TestInfo testInfo;

    @Autowired
    protected ObjectMapper objectMapper;


    @BeforeEach
    void setup(TestInfo testInfo) {
        this.testInfo = testInfo;
    }

    public String getTestClass() {
        return testInfo.getTestClass().get().getSimpleName();
    }


    protected <T> T objectFromFile(Class<T> objectClass, String fileName) throws JsonProcessingException {
        return objectMapper.readerFor(objectClass).readValue(readFile(fileName));
    }

    protected String readFile(String fileName) {
        File file = new File(String.format("./src/test/resource/json/%s/%s", getTestClass(), fileName));

        return readFile(file);
    }

    protected String readFile(File readFile) {
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

    protected void assertJsonEqualsFile(String expectedFileName, String actualStr, String... ignoreFields) {
        assertJsonEqualsStr(expectedFileName, readFile(expectedFileName), actualStr, ignoreFields);
    }


    protected void assertJsonEqualsStr(String fileName, String expectedStr, String actualStr, String... ignoreFields) {
        try {
            try {
                // "STRICT" pro fallimento test in presenza di campi aggiuntivi
                if (ignoreFields == null || ignoreFields.length == 0) {
                    JSONAssert.assertEquals(expectedStr, actualStr, JSONCompareMode.STRICT);
                } else {
                    Customization[] customizationsArray = new Customization[ignoreFields.length];
                    for (int i = 0; i < ignoreFields.length; i++) {
                        customizationsArray[i] = new Customization(ignoreFields[i], (o1, o2) -> true);
                    }
                    JSONAssert.assertEquals(expectedStr, actualStr, new CustomComparator(JSONCompareMode.STRICT, customizationsArray));
                }

            } catch (AssertionError ex) {
                FileUtils.writeStringToFile(new File(String.format("./target/actual/%s", fileName)), actualStr, StandardCharsets.UTF_8.name());
                fail(ex);
            }
        } catch (JSONException | IOException ex) {
            log.error(ex.getMessage(), ex);
            fail(ex);
        }
    }


}
