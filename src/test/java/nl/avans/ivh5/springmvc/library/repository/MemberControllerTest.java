package nl.avans.ivh5.springmvc.library.repository;

import nl.avans.ivh5.springmvc.library.controller.MemberController;
import nl.avans.ivh5.springmvc.library.selenium.SeleniumTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SeleniumTest(driver = ChromeDriver.class, baseUrl = "http://localhost:8080")
@Ignore
public class MemberControllerTest {

    @Mock
//    PolicyService policyService;

    @InjectMocks
    MemberController controllerUnderTest;

    private MockMvc mockMvc;

    @Before
    public void setup() {

        // this must be called for the @Mock annotations above to be processed
        // and for the mock service to be injected into the controller under
        // test.
        MockitoAnnotations.initMocks(this);

        this.mockMvc = MockMvcBuilders.standaloneSetup(controllerUnderTest).build();

    }

    @Test
    public void createOrUpdateFailsWhenInvalidDataPostedAndSendsUserBackToForm() throws Exception {
        // POST no data to the form (i.e. an invalid POST)
        mockMvc.perform(post("/policies/persist"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasErrors("policy"))
                .andExpect(view().name("createOrUpdatePolicy"));
    }

    @Test
    public void createOrUpdateSuccessful() throws Exception {




        //  >>>>>>>  Kijken!   when(policyService.save(isA(Policy.class))).thenReturn(new Policy());

        mockMvc.perform(
                post("/repository/create")
                    .param("companyName", "Company Name")
                    .param("name", "Name")
                    .param("effectiveDate", "2001-01-01"))
                .andExpect(status().isOk())
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("list"));
    }
}