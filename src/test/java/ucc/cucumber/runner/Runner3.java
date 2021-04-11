package ucc.cucumber.runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = {"src/test/resources/features"},
glue = {"ucc.i.steps","ucc.testbase","ucc.utils","ucc.cr.steps","ucc.cj.steps","ucc.com.steps"})
//tags = {"@testACS"})

public class Runner3 {


}
