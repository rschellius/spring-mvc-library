package nl.avans.ivh5.example.springmvc.mockito.book;

import nl.avans.ivh5.example.springmvc.book.Book;
import nl.avans.ivh5.example.springmvc.book.BookController;
import nl.avans.ivh5.example.springmvc.book.BookService;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 *
 */
public class BookControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(BookControllerTest.class);

    @InjectMocks
    private BookController mockBookController;

    @Mock
    private BookService mockBookService;

    private MockMvc mockMvc;
    private static final Long ean = 12345L;
    private static Book book = new Book.Builder(ean, "Titel", "Auteur" ).build();

    @Before
    public void setup() {

        logger.info("---- setUp ----");
        // this must be called for the @Mock annotations above to be processed
        // and for the mock service to be injected into the controller under
        // test.
        MockitoAnnotations.initMocks(this);

        mockBookService = mock(BookService.class);

        this.mockMvc = MockMvcBuilders.standaloneSetup(mockBookController).build();
    }

    @After
    public void tearDown() {
        logger.info("---- tearDown ----");
    }

    /**
     * Zoek een boek, en vind het.
     *
     * @throws Exception
     */
    @Ignore
    @Test
    public void testGetAndFindBookByEAN() throws Exception {
        logger.info("---- testGetBookByEAN ----");

        when(mockBookService.findByEAN(anyLong())).thenReturn(book);

        mockMvc.perform(get("/book/" + ean.intValue()))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasErrors("policy"))
                .andExpect(view().name("createOrUpdatePolicy"));

    }


    @Ignore
    @Test
    public void createOrUpdateFailsWhenInvalidDataPostedAndSendsUserBackToForm() throws Exception {
        // POST no data to the form (i.e. an invalid POST)
        mockMvc.perform(post("/policies/persist"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasErrors("policy"))
                .andExpect(view().name("createOrUpdatePolicy"));
    }

    @Ignore
    @Test
    public void createOrUpdateSuccessful() throws Exception {


        //  >>>>>>>  Kijken!   when(policyService.save(isA(Policy.class))).thenReturn(new Policy());

        mockMvc.perform(
                post("/member/create")
                        .param("companyName", "Company Name")
                        .param("name", "Name")
                        .param("effectiveDate", "2001-01-01"))
                .andExpect(status().isOk())
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("list"));
    }
}