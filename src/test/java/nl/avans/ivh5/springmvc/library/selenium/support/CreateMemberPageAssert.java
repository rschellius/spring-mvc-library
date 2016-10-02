package nl.avans.ivh5.springmvc.library.selenium.support;

import org.assertj.core.api.AbstractAssert;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateMemberPageAssert extends AbstractAssert<CreateMemberPageAssert, CreateMemberPage> {

    protected CreateMemberPageAssert(CreateMemberPage createMemberPage) {
        super(createMemberPage, CreateMemberPageAssert.class);
    }

    public CreateMemberPageAssert hasActuatorLink(String... values) {
        assertThat(getLinkNames()).contains(values);
        return this;
    }

    public CreateMemberPageAssert hasNoActuatorLink(String... values) {
        assertThat(getLinkNames()).doesNotContain(values);
        return this;
    }

    private List<String> getLinkNames() {
        List<WebElement> actuatorLinks = actual.getActuatorLinks();
        return actuatorLinks.stream()
                            .map(WebElement::getText).collect(Collectors.toList());
    }
}
