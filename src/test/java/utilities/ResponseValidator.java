package utilities;

import java.io.File;

import java.time.Instant;
import java.util.List;

import org.testng.Assert;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class ResponseValidator {

    public static void validateStatusCode(int actualStatusCode, int expStatusCode) {
        assertEquals(actualStatusCode, expStatusCode, "Status code validation failed!");
    }

    public static void validateContentType(String actualContentType, String expContentType) {
        assertEquals(actualContentType, expContentType, "Content type validation failed!");
    }

    public static void validateDataType(Response response, String jsonPath, Class<?> expectedType) {
        Object value = response.jsonPath().get(jsonPath);
        Assert.assertNotNull(value, "Value at JSON path '" + jsonPath + "' is null!");
        assertTrue(expectedType.isInstance(value), "Data type validation failed for JSON path '" + jsonPath
                + "'. Expected: " + expectedType.getSimpleName() + ", Actual: " + value.getClass().getSimpleName());
    }

    public static void validateData(String actualValue, String expectedValue) {
        if (actualValue == null && expectedValue == null) {
            return;
        }
        assertEquals(actualValue, expectedValue, "Data validation failed!");
    }

    // validate JSON schema
    public static void validateJsonSchema(Response response, String schemaFileName) {
        try {
            // Path to schema file
            File schemaFile = new File("src/test/resources/Schema/" + schemaFileName);

            // Validate response against the schema
            response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(schemaFile));
            System.out.println("Schema validation successful for: " + schemaFile);
        } catch (Exception e) {
            Assert.fail("Schema validation failed: " + e.getMessage());
        }

    }

    public static void validateDateFormat(Response response, String jsonPath) {
        List<String> dates = response.jsonPath().getList(jsonPath, String.class);
        assertTrue(dates != null && !dates.isEmpty(), "classScheduledDates should not be empty");


        for (String date : dates) {
            assertTrue(isValidISODate(date), String.format("Invalid date format: %s", date));
        }
    }

    private static boolean isValidISODate(String date) {
        try {
            Instant.parse(date); // Checks if the string follows ISO 8601 format
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
