package testrunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = {"src/test/resources/features/"},
        glue = {"stepDefinitions"},
//        tags = "@admin-management",
        tags = "@schema-validation",
        plugin = {"pretty",
                "html:target/BridgeBuilders.html",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"}
)

public class TestRunner extends AbstractTestNGCucumberTests {


}