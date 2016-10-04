package nl.avans.ivh5.springmvc.library.selenium;

import nl.avans.ivh5.springmvc.library.selenium.support.HomePage;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SeleniumTest(driver = ChromeDriver.class, baseUrl = "http://localhost:8080")
public class HomeControllerTest {

    @Autowired
    private WebDriver driver;

    private HomePage homePage;

    @Before
    public void setUp() throws Exception {
        homePage = PageFactory.initElements(driver, HomePage.class);
    }

    @Test
    @Ignore
    public void containsActuatorLinks() {
        homePage.assertThat()
                .hasActuatorLink("autoconfig", "beans", "configprops", "dump", "env", "health", "info", "metrics", "mappings", "trace")
                .hasNoActuatorLink("shutdown");
    }

    @Test
    @Ignore
    public void failingTest() {
        homePage.assertThat()
                .hasNoActuatorLink("autoconfig");
    }
}