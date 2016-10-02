package nl.avans.ivh5.springmvc.library.selenium.support;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CreateMemberPage {

    //
    @FindBy(xpath = "//table//td/p/a")
    private List<WebElement> actuatorLinks;

    private final WebDriver driver;

    public CreateMemberPage(WebDriver driver) {
        this.driver = driver;
    }

    public CreateMemberPageAssert assertThat() {
        return new CreateMemberPageAssert(this);
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public List<WebElement> getActuatorLinks() {
        return actuatorLinks;
    }
}
