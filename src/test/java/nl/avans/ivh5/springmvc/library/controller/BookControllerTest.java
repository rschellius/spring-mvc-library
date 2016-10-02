package nl.avans.ivh5.springmvc.library.controller;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import nl.avans.ivh5.springmvc.library.service.BookService;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(classes = {WebMvcConfig.class, MockDataConfig.class})
@WebAppConfiguration
@WebMvcTest(BookController.class)
public class BookControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(BookControllerTest.class);

    @Autowired
    WebApplicationContext context;

    WebClient webClient;

    @MockBean
    private BookService bookService;


    @Before
    public void setup() {
        webClient = MockMvcWebClientBuilder
                .webAppContextSetup(context)
                .contextPath("")
                .build();
    }

    @After
    public void cleanup() {
        this.webClient.close();
    }





    @Ignore
    @Test
    public void testExample() throws Exception {
        Long ean = 9789023457299L;

        given(this.bookService.findByEAN(ean).getTitle()).willReturn("Bonita Avenue");
        HtmlPage page = this.webClient.getPage("http://localhost:8080/book/9789023457299");
        assertThat(page.getBody().getTextContent()).isEqualTo("Honda Civic");
    }

    @Ignore
    @Test
    public void showBookPage() throws IOException {
        // Load the Create Message Form
        HtmlPage bookPage = webClient.getPage("http://localhost:8080/book/9789023457299");

        // verify we successfully created a message and displayed the newly create message
        assertThat(bookPage.getUrl().toString()).endsWith("/book/9789023457299");
        assertThat(bookPage.getTitleText()).isEqualTo("Avans Bieb");
//        String summary = newMessagePage.getHtmlElementById("summary").getTextContent();
//        assertThat(summary).isEqualTo("Spring Rocks");
//        String text = newMessagePage.getHtmlElementById("text").getTextContent();
//        assertThat(text).isEqualTo("In case you didn't know, Spring Rocks!");
    }

    @Ignore
    @Test
    public void createMessage() throws IOException {
        // Load the Create Message Form
        HtmlPage createMsgFormPage = webClient.getPage("http://localhost/messages/form");

        // Submit the create message form
        HtmlForm form = createMsgFormPage.getHtmlElementById("messageForm");
        HtmlTextInput summaryInput = createMsgFormPage.getHtmlElementById("summary");
        summaryInput.setValueAttribute("Spring Rocks");
        HtmlTextArea textInput = createMsgFormPage.getHtmlElementById("text");
        textInput.setText("In case you didn't know, Spring Rocks!");
        HtmlSubmitInput submit = form.getOneHtmlElementByAttribute("input", "type", "submit");
        HtmlPage newMessagePage = submit.click();

        // verify we successfully created a message and displayed the newly create message
        assertThat(newMessagePage.getUrl().toString()).endsWith("/messages/123");
        String id = newMessagePage.getHtmlElementById("id").getTextContent();
        assertThat(id).isEqualTo("123");
        String summary = newMessagePage.getHtmlElementById("summary").getTextContent();
        assertThat(summary).isEqualTo("Spring Rocks");
        String text = newMessagePage.getHtmlElementById("text").getTextContent();
        assertThat(text).isEqualTo("In case you didn't know, Spring Rocks!");
    }
}