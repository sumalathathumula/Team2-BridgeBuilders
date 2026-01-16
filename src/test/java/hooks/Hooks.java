package hooks;

import context.ScenarioContext;
import io.cucumber.java.After;

public class Hooks {
	@After
    public void cleanUp() {
        ScenarioContext.getInstance().clear();
    }

}
