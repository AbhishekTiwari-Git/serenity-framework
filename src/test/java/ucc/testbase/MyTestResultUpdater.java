package ucc.testbase;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import net.serenitybdd.core.webdriver.RemoteDriver;
import net.serenitybdd.core.webdriver.enhancers.AfterAWebdriverScenario;
import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.util.EnvironmentVariables;

public class MyTestResultUpdater implements AfterAWebdriverScenario {
	
	public void apply(EnvironmentVariables environmentVariables,
            TestOutcome testOutcome,
            WebDriver driver) {
    if ((driver == null) || (!RemoteDriver.isARemoteDriver(driver))) {
        return;
    }

    Cookie cookie = new Cookie("testPassed",
                                testOutcome.isFailure() || testOutcome.isError() || testOutcome.isCompromised() ? "false" : "true");
    driver.manage().addCookie(cookie);
 }

}
