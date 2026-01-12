package stepDefinitions;

import static io.restassured.RestAssured.given;
import io.cucumber.java.en.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.ConfigReader;

public class LoginSeps {

	 RequestSpecification req_Base;
	 Response response;

	@Given("admin sets No Auth")
	public void admin_sets_no_auth() {
		// Write code here that turns the phrase above into concrete actions
		req_Base = new RequestSpecBuilder().setBaseUri(ConfigReader.getBaseUrl()).setContentType(ContentType.JSON)
				.build();
	}

	@Given("Admin creates request with valid credentials")
	public void admin_creates_request_with_valid_credentials() {
		req_Base = given().spec(req_Base)
				.body("{ \"userLoginEmailId\": \"team2@gmail.com\",\"password\": \"ApiHackathon2@2\"}");

	}

	@When("Admin calls Post HTTPS method with valid endpoint")
	public void admin_calls_post_https_method_with_valid_endpoint() {

		response = given()
				.spec(req_Base)
				.when()
				.log().body()
				.post("/login");

	}

	@Then("Admin receives {int} created with auto generated token")
	public void admin_receives_created_with_auto_generated_token(Integer s) {
		response.then().statusCode(s);
		System.out.println("body: "+response.asString());

	}

}
