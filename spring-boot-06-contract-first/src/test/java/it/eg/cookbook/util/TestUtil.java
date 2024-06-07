package it.eg.cookbook.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.fail;

@Slf4j
public abstract class TestUtil {

    public static ObjectMapper defaultObjectMapper() {
        return JsonMapper
                .builder()
                .addModules(new Jdk8Module(), new JavaTimeModule(), new ParameterNamesModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
                .configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, false)
                .enable(SerializationFeature.INDENT_OUTPUT)
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                // Non sono serilizzare i campi non valorizzati (per evitare problemi con integrazioni Feign)
                //.serializationInclusion(JsonInclude.Include.ALWAYS)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .defaultTimeZone(TimeZone.getDefault())
                .build();
    }

    public static <T> T readObject(String fileName, Class<T> valueType) throws JsonProcessingException {
        ObjectMapper objectMapper = defaultObjectMapper();
        return objectMapper.readValue(readFile(fileName), valueType);
    }

    public static String readFile(String fileName) {
        File file = new File(String.format("./src/test/resources/json/%s", fileName));

        return readFile(file);
    }

    public static String readFile(File readFile) {
        if (!readFile.isFile()) {
            fail("File " + readFile + " non leggibile");
        }

        if (!readFile.exists()) {
            fail("File " + readFile + " non trovato");
        }

        try {
            return FileUtils.readFileToString(readFile, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
            fail("File " + readFile + " non leggibile", ex);
            return null;
        }
    }

    public static void assertJsonEqualsFile(String expectedFileName, String actualStr, String... ignoreFields) {
        assertJsonEqualsStr(expectedFileName, readFile(expectedFileName), actualStr, ignoreFields);
    }


    public static void assertJsonEqualsStr(String fileName, String expectedStr, String actualStr, String... ignoreFields) {
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
