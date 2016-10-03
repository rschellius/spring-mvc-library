package nl.avans.ivh5.springmvc.library.controller;

import nl.avans.ivh5.springmvc.config.TestContext;
import nl.avans.ivh5.springmvc.config.ApplicationContext;
import nl.avans.ivh5.springmvc.library.service.BookService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, ApplicationContext.class})
@WebAppConfiguration
public class StandaloneBookControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private BookService bookServiceMock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        //We have to reset our mock between tests because the mock objects
        //are managed by the Spring container. If we would not reset them,
        //stubbing and verified behavior would "leak" from one test to another.
        Mockito.reset(bookServiceMock);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Ignore
    @Test
    public void someTestCase_ThatTestsSomething_AndReturnsResult(){
        // template to fill in
    }
}