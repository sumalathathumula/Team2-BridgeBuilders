//package stepDefinitions;
//
//import static io.restassured.RestAssured.given;
//import static org.assertj.core.api.Assertions.assertThat;
//
//import context.HttpContext;
//import io.cucumber.java.en.*;
//import io.restassured.builder.RequestSpecBuilder;
//import io.restassured.http.ContentType;
//import io.restassured.specification.RequestSpecification;
//import lombok.extern.slf4j.Slf4j;
//import utilities.ConfigReader;
//
//@Slf4j
//public class LogoutStepDefinition {
//    private static final String BASE_URI = "https://lms-marchapi-hackathon-a258d2bbd43b.herokuapp.com/lms";
//    private final HttpContext context;
//
//    public LogoutStepDefinition(HttpContext context) {
//        this.context = context;
//    }
//
//    // --- HELPER METHOD: Reduces duplication in @Given steps ---
//    private void buildRequest(String email, String password, String uri) {
//        String reqBody = """
//                {
//                    "userLoginEmailId":"%s",
//                    "password":"%s"
//                }
//                """;
//
//        String body = String.format(reqBody, email, password);
//
//        // 2. Build the request fluently
//        RequestSpecification req = given()
//                .baseUri(uri)
//                .contentType(ContentType.JSON)
//                .body(body);
//
//        context.setRequest(req);
//    }
//
//    // Background Steps
//    @Given("Admin sets No Auth")
//    public void admin_sets_no_auth() {
//        // Write code here that turns the phrase above into concrete actions
//        var req = new RequestSpecBuilder().setBaseUri(ConfigReader.getBaseUrl()).setContentType(ContentType.JSON)
//                .build();
//    }
