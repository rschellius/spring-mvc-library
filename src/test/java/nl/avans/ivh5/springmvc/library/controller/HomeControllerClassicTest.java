package nl.avans.ivh5.springmvc.library.controller;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootConfiguration
@ContextConfiguration
@SpringBootTest
// =======
//@RunWith(SpringJUnit4ClassRunner.class)
// @SpringApplicationConfiguration(classes = Application.class)
// @WebAppConfiguration
public class HomeControllerClassicTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;


    /**
     *  Deze testcase moet nog uitgewerkt worden. Je test hiermee de homepage, maar om die te kunnen
     *  tonen moet er een databaseconnectie zijn, een connectie met de repositories, en we moeten boeken
     *  op de frontpage laten zien. Dat proces moet dus gemockt worden.
     *
     *  Nu nog niet uitgewerkt, ToDo.
     */



    @Before
    public void setup() {
//        mockMvc = mockMvc = standaloneSetup(new HomeController())
//                .build();
    }

    @Test
    @Ignore     // DIT MOET WEG OM TE TESTCASE MEE TE NEMEN IN DE TESTRUNNER !!!!!!!!!!!!!!!!!!!!!!!
    public void verifiesHomePageLoads() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}