package nl.avans.ivh5.springmvc.common.controller;

import nl.avans.ivh5.springmvc.config.ApplicationContext;
import nl.avans.ivh5.springmvc.config.TestContext;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Petri Kainulainen
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, ApplicationContext.class})
@WebAppConfiguration
public class ErrorControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void requestNonExistingPage_AndExpect404() throws Exception {
        mockMvc.perform(get("/a_page_that_does_not_exist"))
                .andExpect(status().isNotFound());
//                .andExpect(view().name("error"))
//                .andExpect(forwardedUrl("error/error"));
    }

    @Ignore
    @Test
    public void showInternalServerErrorPage() throws Exception {
        mockMvc.perform(get("/error/error"))
                .andExpect(status().isOk())
                .andExpect(view().name(ErrorController.VIEW_INTERNAL_SERVER_ERROR))
                .andExpect(forwardedUrl("/WEB-INF/jsp/error/error.jsp"));
    }
}
