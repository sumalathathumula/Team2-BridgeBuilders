package hooks;

import context.ScenarioContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;


public class Hooks {
	
	private ScenarioContext scenario;

    @Before
    public void beforeScenario(ScenarioContext scenario) {
        this.scenario = scenario;
    }
	 public void logResponse(String response) {
	       // scenario.log(response);
	    }
	
	@After
    public void cleanUp() {
        ScenarioContext.getInstance().clear();
    }

}
