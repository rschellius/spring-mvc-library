package nl.avans.ivh5.springmvc.library.controller;

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
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    MockMvc mockMvc;

    @MockBean
    private MemberService memberService;


    @Before
    public void setup() {
        logger.info("---- setUp ----");

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @After
    public void tearDown() {
        logger.info("---- tearDown ----");
    }

    @Test
    public void PostEmptyForm() throws Exception {

        logger.info("---- PostEmptyForm ----");

        mockMvc.perform(post("/member/create")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .sessionAttr("member", new Member())
        )
                // Ondanks dat de VALIDATIE failt kan de HTML pagina wel gevonden worden = HTTP 200
                .andExpect(status().isOk())
                .andExpect(view().name("views/member/create"))
                // Attributen hieronder zijn de attributen van de Member class.
                .andExpect(model().attributeHasFieldErrors("member", "firstName"))
                .andExpect(model().attributeHasFieldErrors("member", "lastName"))
                .andExpect(model().attribute("member", hasProperty("firstName", isEmptyOrNullString())))
                .andExpect(model().attribute("member", hasProperty("emailAddress", isEmptyOrNullString())));
    }

    @Test
    public void EmailInvalid() throws Exception {

        mockMvc.perform(post("/member/create")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                //
                // Deze params zijn de case sensitive attributen van de (in dit geval) Member class.
                //
                .param("firstName", TestContext.MEMBER_FIRSTNAME)
                .param("lastName", TestContext.MEMBER_LASTNAME)
                .param("street", TestContext.MEMBER_STREET)
                .param("houseNumber", TestContext.MEMBER_HOUSENUMBER)
                .param("city", TestContext.MEMBER_CITY)
                .param("phoneNumber", TestContext.MEMBER_PHONENUMBER)
                .param("emailAddress", TestContext.MEMBER_EMAILADDRESS_INVALID)
                .sessionAttr("member", new Member())
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("views/member/create"))
                //
                // Attributen hieronder zijn de namen van de inputvelden van het formulier.
                //
                .andExpect(model().attributeHasFieldErrors("member", "emailAddress"))
                .andExpect(model().attribute("member", hasProperty("emailAddress", equalTo(TestContext.MEMBER_EMAILADDRESS_INVALID))))
                // Deze foutmelding is gezet in Member.java. Kun je aanpassen via string vanuit properties file.
                .andExpect(content().string(containsString("{invalid.email}")));
    }

    @Test
    public void SuccessfullyCreateMember() throws Exception {

        mockMvc.perform(post("/member/create")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                //
                // Deze params zijn de case sensitive attributen van de (in dit geval) Member class.
                //
                .param("firstName", TestContext.MEMBER_FIRSTNAME)
                .param("lastName", TestContext.MEMBER_LASTNAME)
                .param("street", TestContext.MEMBER_STREET)
                .param("houseNumber", TestContext.MEMBER_HOUSENUMBER)
                .param("city", TestContext.MEMBER_CITY)
                .param("phoneNumber", TestContext.MEMBER_PHONENUMBER)
                .param("emailAddress", TestContext.MEMBER_EMAILADDRESS_VALID)
                .sessionAttr("member", new Member())
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().hasNoErrors());
    }

}