package nl.avans.ivh5.springmvc.library.controller;

import com.gargoylesoftware.htmlunit.WebClient;
import nl.avans.ivh5.springmvc.config.ApplicationContext;
import nl.avans.ivh5.springmvc.config.TestContext;
import nl.avans.ivh5.springmvc.library.model.Book;
import nl.avans.ivh5.springmvc.library.model.Copy;
import nl.avans.ivh5.springmvc.library.model.Member;
import nl.avans.ivh5.springmvc.library.service.BookService;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebConnection;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Petri Kainulainen
 *
 * Deze code is voor een belangrijk dee ontstaan uit een artikel op de site van Petri Kainulainen.
 * Het beste is om het artikel van Petri op zijn website zelf te lezen. Zie
 * https://www.petrikainulainen.net/programming/spring-framework/unit-testing-of-spring-mvc-controllers-configuration/
 *
 */
@SuppressWarnings("Duplicates")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, ApplicationContext.class})
@WebAppConfiguration
public class WebApplicationContextBookControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(WebApplicationContextBookControllerTest.class);

    private static final Long BOOK_EAN = 1234L;
    private static final String BOOK_TITLE = "De Titel";
    private static final String BOOK_AUTHOR = "De Schrijver";
    private static final String MEMBER_FIRSTNAME = "Voornaam";
    private static final String MEMBER_LASTNAME = "Achternaam";

    private MockMvc mockMvc;
    private WebClient webClient;

    @Autowired
    private BookService bookServiceMock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        logger.info("---- setUp ----");
        //We have to reset our mock between tests because the mock objects
        //are managed by the Spring container. If we would not reset them,
        //stubbing and verified behavior would "leak" from one test to another.
        Mockito.reset(bookServiceMock);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        webClient = new WebClient();
        webClient.setWebConnection(new MockMvcWebConnection(mockMvc));
    }

    @After
    public void tearDown() {
        logger.info("---- tearDown ----");
        this.webClient.close();
    }

    @Ignore
    @Test
    public void showBookDetails_ShouldCreateFormObjectAndRenderAddTodoForm() throws Exception {

        Book book  = new Book.Builder(BOOK_EAN, BOOK_TITLE, BOOK_AUTHOR).build();

        Copy copy = new Copy(BOOK_EAN);
        copy.setLoanDate(new Date());
        copy.setFirstName(MEMBER_FIRSTNAME);
        copy.setLastName(MEMBER_LASTNAME);
        List<Copy> copies = new ArrayList<>();
        copies.add(copy);

        List<Member> members = new ArrayList<>();
        members.add(new Member());

        bookServiceMock = mock(BookService.class);
        when(bookServiceMock.findByEAN(BOOK_EAN)).thenReturn(book);
        when(bookServiceMock.findLendingInfoByBookEAN(BOOK_EAN)).thenReturn(copies);
        when(bookServiceMock.findAllMembers()).thenReturn(members);

        // Deze werkt nog niet helemaal ...
        mockMvc.perform(get("/book/" + BOOK_EAN))
                .andExpect(status().isOk());
    }

}
