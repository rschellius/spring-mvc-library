package nl.avans.ivh5.library.member;

import nl.avans.ivh5.library.selenium.support.SeleniumTest;
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
public class MemberControllerTest {

    @Autowired
    private WebDriver driver;

    private CreateMemberPage createMemberPage;

    @Before
    public void setUp() throws Exception {
        createMemberPage = PageFactory.initElements(driver, CreateMemberPage.class);
    }

    @Test
    @Ignore
    public void containsActuatorLinks() {
        createMemberPage
                .assertThat()
                .hasActuatorLink("autoconfig", "beans", "configprops", "dump", "env", "health", "info", "metrics", "mappings", "trace")
                .hasNoActuatorLink("shutdown");
    }

    @Test
    @Ignore
    public void failingTest() {
        createMemberPage
                .assertThat()
                .hasNoActuatorLink("autoconfig");
    }
}