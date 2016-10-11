package nl.avans.ivh5.springmvc.library.controller;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import nl.avans.ivh5.springmvc.common.exception.BookNotFoundException;
import nl.avans.ivh5.springmvc.config.ApplicationContext;
import nl.avans.ivh5.springmvc.config.TestContext;
import nl.avans.ivh5.springmvc.library.model.Book;
import nl.avans.ivh5.springmvc.library.model.Copy;
import nl.avans.ivh5.springmvc.library.model.Member;
import nl.avans.ivh5.springmvc.library.service.BookService;
import nl.avans.ivh5.springmvc.library.service.CopyService;
import nl.avans.ivh5.springmvc.library.service.MemberService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
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
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 */
@SuppressWarnings("Duplicates")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, ApplicationContext.class})
@WebAppConfiguration
public class BookControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(BookControllerTest.class);

    @Autowired
    WebApplicationContext context;

    WebClient webClient;

    @MockBean
    private BookService bookService;

    @Mock
    private CopyService copyService;

    @Mock
    private MemberService memberService;

    @Mock
    ArrayList<Copy> copies;

    final private String BOOK_TITLE = "A Long Dummy Titel of this book";
    final private String BOOK_AUTHOR = "Dummy Author";
    final private Long BOOK_EAN = 12345L;

    @Before
    public void setup() {
        logger.info("---- setUp ----");

        MockitoAnnotations.initMocks(this);

        webClient = MockMvcWebClientBuilder
                .webAppContextSetup(context)
                .contextPath("")
                .build();
    }

    @After
    public void tearDown() {
        logger.info("---- tearDown ----");
        this.webClient.close();
    }

    @Test
    public void testFindBookById_SuccessfullyFindsBook() throws IOException {
        logger.info("---- testFindBookById_SuccessfullyFindsBook ----");

        // Het boek dat we gaan vinden
        Book book = new Book.Builder(BOOK_EAN, BOOK_TITLE, BOOK_AUTHOR).build();
        try {
            when(bookService.findByEAN(anyLong())).thenReturn(book);
        } catch (BookNotFoundException ex){
            logger.error(ex.getMessage());
        }

        Member dummyMember = new Member();
        List<Member> members = new ArrayList<>();
        members.add(dummyMember);
        members.add(dummyMember);
        members.add(dummyMember);
        members.add(dummyMember);
        when(memberService.findAllMembers()).thenReturn(members);

        Copy dummyCopy = mock(Copy.class);
        copies.add(dummyCopy);
//        when(copyService.findLendingInfoByBookEAN(anyLong())).thenReturn(copies);
        when(copies.size()).thenReturn(1);


        List<Copy> spyCopies = Mockito.spy(new ArrayList<Copy>());
        spyCopies.add(dummyCopy);
        spyCopies.add(dummyCopy);
        logger.info("copies size = " + spyCopies.size());

        when(copyService.findLendingInfoByBookEAN(anyLong())).thenReturn(spyCopies);
        Mockito.doReturn(100).when(spyCopies).size();

        // Load the /book/id page
        HtmlPage bookPage = webClient.getPage("http://localhost:8080/book/" + BOOK_EAN);

        // verify we successfully created a message and displayed the newly create message
        assertThat(bookPage.getUrl().toString()).endsWith("/book/" + BOOK_EAN);
        assertThat(bookPage.getTitleText()).isEqualTo("Avans Bieb");
    }

}