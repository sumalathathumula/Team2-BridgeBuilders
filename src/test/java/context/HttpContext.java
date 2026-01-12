package context;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HttpContext {
    private Response response;

    private RequestSpecification request;
}
