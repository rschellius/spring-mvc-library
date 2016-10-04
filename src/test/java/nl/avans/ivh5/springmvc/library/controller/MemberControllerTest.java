package nl.avans.ivh5.springmvc.library.controller;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import nl.avans.ivh5.springmvc.config.ApplicationContext;
import nl.avans.ivh5.springmvc.config.TestContext;
import nl.avans.ivh5.springmvc.library.model.Member;
import nl.avans.ivh5.springmvc.library.service.MemberService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 */
@SuppressWarnings("Duplicates")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, ApplicationContext.class})
@WebAppConfiguration
public class MemberControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(MemberControllerTest.class);

    @Autowired
    WebApplicationContext context;

    WebClient webClient;

    @MockBean
    private MemberService memberService;


    @Before
    public void setup() {
        logger.info("---- setUp ----");

        webClient = MockMvcWebClientBuilder
                .webAppContextSetup(context)
                .contextPath("")
                .build();
        // Voorkom dat de test failt op JavaScript errors
        webClient.getOptions().setThrowExceptionOnScriptError(false);
    }

    @After
    public void tearDown() {
        logger.info("---- tearDown ----");
        this.webClient.close();
    }

    @Test
    public void testCreateMemberPage() throws Exception {

        HtmlPage page = this.webClient.getPage("http://localhost:8080/member/create");

        HtmlForm form = page.getHtmlElementById("createMemberForm");

        HtmlTextInput inputFirstName = page.getHtmlElementById("firstname");
        inputFirstName.setValueAttribute("Voornaam");
        HtmlTextInput inputlastName = page.getHtmlElementById("firstname");
        inputlastName.setValueAttribute("Achternaam");
        HtmlTextInput inputStreet = page.getHtmlElementById("street");
        inputStreet.setValueAttribute("Straatnaam");
        HtmlTextInput inputHousenumber = page.getHtmlElementById("housenumber");
        inputHousenumber.setValueAttribute("45");
        HtmlTextInput inputCity = page.getHtmlElementById("city");
        inputCity.setValueAttribute("Stadnaam");
        HtmlTextInput inputPhone = page.getHtmlElementById("phone");
        inputPhone.setValueAttribute("1234154314325");
        HtmlTextInput inputEmail = page.getHtmlElementById("email");
        inputEmail.setValueAttribute("test@test.com");

        HtmlButton submit =
                form.getOneHtmlElementByAttribute("button", "type", "submit");

        Member newMember = new Member();
        newMember.setFirstName("test");
        MemberService memberService = mock(MemberService.class);
        when(memberService.create(anyObject())).thenReturn(newMember);

        HtmlPage newPage = submit.click();


        // Hier moeten we nu testen dat de Member die we gemaakt hebben,
        // ook daadwerkelijk door de MemberService is ontvangen.

        // ToDo!


    }

    @Test
    public void showBookPage() throws IOException {
        // Load the page
        HtmlPage bookPage = webClient.getPage("http://localhost:8080/book");

        assertThat(bookPage.getTitleText()).isEqualTo("Avans Bieb");
    }

}