package utilities;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpecFactory {
	
	private static RequestSpecBuilder baseBuilder() {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigReader.getBaseUrl())
                .setContentType(ContentType.JSON);
    }

    public static RequestSpecification withoutAuth() {
        return baseBuilder().build();
    }

    public static RequestSpecification withAuth(String token) {
        return baseBuilder()
                .addHeader("Authorization", "Bearer " + token)
                .build();
    }

}