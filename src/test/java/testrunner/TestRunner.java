package testrunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
			features = {"src/test/resources/Features/"}, 

			glue = {"stepDefinitions"},	
		    tags ="@admin-login", 
		 // tags ="@login  or @ProgramModule or @batch or @GetBatches or @GetBatchByBatchId or  @UpdateBatchByBatchId or @GetBatchByBatchName or ",
			plugin = {"pretty", "html:target/Team1_LMSReport.html","io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"}

			
		)
	

	public class TestRunner extends AbstractTestNGCucumberTests {
		
	  
	}