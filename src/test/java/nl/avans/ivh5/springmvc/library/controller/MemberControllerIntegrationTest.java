package nl.avans.ivh5.springmvc.library.controller;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import nl.avans.ivh5.springmvc.config.ApplicationContext;
import nl.avans.ivh5.springmvc.config.TestContext;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static nl.avans.ivh5.springmvc.config.TestContext.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
@SuppressWarnings("Duplicates")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, ApplicationContext.class})
@WebAppConfiguration
public class MemberControllerIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(MemberControllerIntegrationTest.class);

    @Autowired
    WebApplicationContext context;

    WebClient webClient;
    MockMvc mockMvc;
    // Selenium WebDriver
//    WebDriver driver;

    @MockBean
    private MemberService memberService;


    @Before
    public void setup() {
        logger.info("---- setUp ----");

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

        webClient = MockMvcWebClientBuilder
//                .mockMvcSetup(mockMvc)
//                .contextPath("")
                .webAppContextSetup(context)
                .build();

        // Voorkom dat de test failt op JavaScript of CSS errors
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);

    }

    @After
    public void tearDown() {
        logger.info("---- tearDown ----");
        if (webClient != null) {
            webClient.close();
        }
    }

    @Test
    public void testCreateMemberPage_SuccessfullyCreateMember() throws Exception {
        logger.info("---- testCreateMemberPage_SuccessfullyCreateMember ----");

        HtmlPage page = this.webClient.getPage("http://localhost:8080/member/create");

        HtmlForm form = page.getHtmlElementById("createMemberForm");

        HtmlTextInput inputFirstName = page.getHtmlElementById("firstname");
        inputFirstName.setValueAttribute(MEMBER_FIRSTNAME);
        HtmlTextInput inputlastName = page.getHtmlElementById("lastname");
        inputlastName.setValueAttribute(MEMBER_LASTNAME);
        HtmlTextInput inputStreet = page.getHtmlElementById("street");
        inputStreet.setValueAttribute(MEMBER_STREET);
        HtmlTextInput inputHousenumber = page.getHtmlElementById("housenumber");
        inputHousenumber.setValueAttribute(MEMBER_HOUSENUMBER);
        HtmlTextInput inputCity = page.getHtmlElementById("city");
        inputCity.setValueAttribute(MEMBER_STREET);
        HtmlTextInput inputPhone = page.getHtmlElementById("phone");
        inputPhone.setValueAttribute(MEMBER_PHONENUMBER);
        HtmlTextInput inputEmail = page.getHtmlElementById("email");
        inputEmail.setValueAttribute(MEMBER_EMAILADDRESS_VALID);

        HtmlButton submit =
                form.getOneHtmlElementByAttribute("button", "type", "submit");

//        Member mockMember = new Member();
//        mockMember.setFirstName("Nieuwe Voornaam");
//        mockMember.setLastName("Nieuwe Achternaam");
//        List<Member> members = new ArrayList<>();
//        members.add(mockMember);
//
//        memberService = mock(MemberService.class);
//        when(memberService.create(any(Member.class))).thenReturn(mockMember);
//        when(memberService.findAllMembers()).thenReturn(members);

        //
        // Hier verzenden we het formulier
        //
        HtmlPage newPage = submit.click();

        logger.debug("---- Status = " + newPage.getWebResponse().getStatusMessage());
        // logger.debug("---- Body = \n" + newPage.getWebResponse().getContentAsString());

        // Hier moeten we nu testen dat de Member die we gemaakt hebben,
        // ook daadwerkelijk door de MemberService is ontvangen.

        assertThat(newPage.getUrl().toString()).endsWith("/member/create");
//        assertThat(newPage.getWebResponse().getContentAsString()).contains("Nieuwe Voornaam Nieuwe Achternaam");


//        String summary = newPage.getHtmlElementById("summary").getTextContent();
//        assertThat(summary).isEqualTo("Spring Rocks");
//        String text = newPage.getHtmlElementById("text").getTextContent();
//        assertThat(text).isEqualTo("In case you didn't know, Spring Rocks!");




    }

}