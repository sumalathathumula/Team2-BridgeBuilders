package io.account;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class HttpContext {
    private Response response;

    private RequestSpecification request;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public RequestSpecification getRequest() {
        return request;
    }

    public void setRequest(RequestSpecification request) {
        this.request = request;
    }
}
