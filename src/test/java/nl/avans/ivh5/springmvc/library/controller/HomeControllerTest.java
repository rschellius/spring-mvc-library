package nl.avans.ivh5.springmvc.library.controller;

import nl.avans.ivh5.springmvc.library.model.Book;
import nl.avans.ivh5.springmvc.library.repository.BookRepository;
import nl.avans.ivh5.springmvc.library.repository.CopyRepository;
import nl.avans.ivh5.springmvc.library.repository.LoanRepository;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootConfiguration
@ContextConfiguration
@SpringBootTest
public class HomeControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(HomeControllerTest.class);

    private MockMvc mockMvc;

    @Mock
    BookRepository bookRepository;

    @Mock
    CopyRepository copyRepository;

    @Mock
    LoanRepository loanRepository;

    @Before
    public void setup() {

        logger.info("---- Setup ----");

        bookRepository = mock(BookRepository.class);
        loanRepository = mock(LoanRepository.class);
        copyRepository = mock(CopyRepository.class);

        mockMvc = standaloneSetup(new HomeController(copyRepository, bookRepository, loanRepository))
                .build();
    }

    @After
    public void tearDown(){
        logger.info("---- tearDown ----");
    }

    @Test
    public void verifiesHomePageLoads() throws Exception {
        logger.info("---- verifiesHomePageLoads ----");

        Book book = new Book.Builder(1234L, "De Titel", "De Schrijver").build();
        ArrayList<Book> booksFoundOnHomePage = new ArrayList<>();
        booksFoundOnHomePage.add(book);
        booksFoundOnHomePage.add(book);
        booksFoundOnHomePage.add(book);
        when(bookRepository.findAll()).thenReturn(booksFoundOnHomePage);

        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().hasNoErrors())
                .andExpect(model().attribute("classActiveHome", Matchers.is("active")))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("books", Matchers.hasSize(3)))
                .andExpect(view().name("views/home/index"));
    }
}