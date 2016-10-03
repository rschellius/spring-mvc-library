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

    @Test
    @Ignore
    public void add_EmptyTodoEntry_ShouldRenderFormViewAndReturnValidationErrorForTitle() throws Exception {
//        mockMvc.perform(post("/todo/add")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .sessionAttr(TodoController.MODEL_ATTRIBUTE_TODO, new TodoDTO())
//        )
//                .andExpect(status().isOk())
//                .andExpect(view().name(TodoController.VIEW_TODO_ADD))
//                .andExpect(forwardedUrl("/WEB-INF/jsp/todo/add.jsp"))
//                .andExpect(model().attributeHasFieldErrors(TodoController.MODEL_ATTRIBUTE_TODO, "title"))
//                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO, hasProperty("id", nullValue())))
//                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO, hasProperty("description", isEmptyOrNullString())))
//                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO, hasProperty("title", isEmptyOrNullString())));
//
//        verifyZeroInteractions(todoServiceMock);
    }

    @Ignore
    @Test
    public void add_DescriptionAndTitleAreTooLong_ShouldRenderFormViewAndReturnValidationErrorsForTitleAndDescription() throws Exception {
//        String title = TestUtil.createStringWithLength(Todo.MAX_LENGTH_TITLE + 1);
//        String description = TestUtil.createStringWithLength(Todo.MAX_LENGTH_DESCRIPTION + 1);
//
//        mockMvc.perform(post("/todo/add")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .param(WebTestConstants.FORM_FIELD_DESCRIPTION, description)
//                .param(WebTestConstants.FORM_FIELD_TITLE, title)
//                .sessionAttr(TodoController.MODEL_ATTRIBUTE_TODO, new TodoDTO())
//        )
//                .andExpect(status().isOk())
//                .andExpect(view().name(TodoController.VIEW_TODO_ADD))
//                .andExpect(forwardedUrl("/WEB-INF/jsp/todo/add.jsp"))
//                .andExpect(model().attributeHasFieldErrors(TodoController.MODEL_ATTRIBUTE_TODO, "title"))
//                .andExpect(model().attributeHasFieldErrors(TodoController.MODEL_ATTRIBUTE_TODO, "description"))
//                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO, hasProperty("id", nullValue())))
//                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO, hasProperty("description", is(description))))
//                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO, hasProperty("title", is(title))));
//
//        verifyZeroInteractions(todoServiceMock);
    }

    @Ignore
    @Test
    public void add_NewTodoEntry_ShouldAddTodoEntryAndRenderViewTodoEntryView() throws Exception {
//        Todo added = new TodoBuilder()
//                .id(ID)
//                .description(DESCRIPTION)
//                .title(TITLE)
//                .build();
//
//        when(todoServiceMock.add(isA(TodoDTO.class))).thenReturn(added);
//
//        String expectedRedirectViewPath = TestUtil.createRedirectViewPath(TodoController.REQUEST_MAPPING_TODO_VIEW);
//
//        mockMvc.perform(post("/todo/add")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .param(WebTestConstants.FORM_FIELD_DESCRIPTION, "description")
//                .param(WebTestConstants.FORM_FIELD_TITLE, "title")
//                .sessionAttr(TodoController.MODEL_ATTRIBUTE_TODO, new TodoDTO())
//        )
//                .andExpect(status().isMovedTemporarily())
//                .andExpect(view().name(expectedRedirectViewPath))
//                .andExpect(redirectedUrl("/todo/1"))
//                .andExpect(model().attribute(TodoController.PARAMETER_TODO_ID, is(ID.toString())))
//                .andExpect(flash().attribute(TodoController.FLASH_MESSAGE_KEY_FEEDBACK, is("Todo entry: title was added.")));
//
//        ArgumentCaptor<TodoDTO> formObjectArgument = ArgumentCaptor.forClass(TodoDTO.class);
//        verify(todoServiceMock, times(1)).add(formObjectArgument.capture());
//        verifyNoMoreInteractions(todoServiceMock);
//
//        TodoDTO formObject = formObjectArgument.getValue();
//
//        assertThat(formObject.getDescription(), is(DESCRIPTION));
//        assertNull(formObject.getId());
//        assertThat(formObject.getTitle(), is(TITLE));
    }

//    @Test
//    public void deleteById_TodoEntryFound_ShouldDeleteTodoEntryAndRenderTodoListView() throws Exception {
//        Todo deleted = new TodoBuilder()
//                .id(ID)
//                .description("Bar")
//                .title("Foo")
//                .build();
//
//        when(todoServiceMock.deleteById(ID)).thenReturn(deleted);
//
//        String expectedRedirectViewPath = TestUtil.createRedirectViewPath(TodoController.REQUEST_MAPPING_TODO_LIST);
//
//        mockMvc.perform(get("/todo/delete/{id}", ID))
//                .andExpect(status().isMovedTemporarily())
//                .andExpect(view().name(expectedRedirectViewPath))
//                .andExpect(flash().attribute(TodoController.FLASH_MESSAGE_KEY_FEEDBACK, is("Todo entry: Foo was deleted.")));
//
//        verify(todoServiceMock, times(1)).deleteById(ID);
//        verifyNoMoreInteractions(todoServiceMock);
//    }
//
//    @Test
//    public void deleteById_TodoEntryNotFound_ShouldRender404View() throws Exception {
//        when(todoServiceMock.deleteById(ID)).thenThrow(new TodoNotFoundException(""));
//
//        mockMvc.perform(get("/todo/delete/{id}", ID))
//                .andExpect(status().isNotFound())
//                .andExpect(view().name(net.petrikainulainen.spring.testmvc.common.controller.ErrorController.VIEW_NOT_FOUND))
//                .andExpect(forwardedUrl("/WEB-INF/jsp/error/404.jsp"));
//
//        verify(todoServiceMock, times(1)).deleteById(ID);
//        verifyNoMoreInteractions(todoServiceMock);
//    }
//
//    @Test
//    public void findAll_ShouldAddTodoEntriesToModelAndRenderTodoListView() throws Exception {
//        Todo first = new TodoBuilder()
//                .id(1L)
//                .description("Lorem ipsum")
//                .title("Foo")
//                .build();
//
//        Todo second = new TodoBuilder()
//                .id(2L)
//                .description("Lorem ipsum")
//                .title("Bar")
//                .build();
//
//        when(todoServiceMock.findAll()).thenReturn(Arrays.asList(first, second));
//
//        mockMvc.perform(get("/"))
//                .andExpect(status().isOk())
//                .andExpect(view().name(TodoController.VIEW_TODO_LIST))
//                .andExpect(forwardedUrl("/WEB-INF/jsp/todo/list.jsp"))
//                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO_LIST, hasSize(2)))
//                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO_LIST, hasItem(
//                        allOf(
//                                hasProperty("id", is(1L)),
//                                hasProperty("description", is("Lorem ipsum")),
//                                hasProperty("title", is("Foo"))
//                        )
//                )))
//                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO_LIST, hasItem(
//                        allOf(
//                                hasProperty("id", is(2L)),
//                                hasProperty("description", is("Lorem ipsum")),
//                                hasProperty("title", is("Bar"))
//                        )
//                )));
//
//        verify(todoServiceMock, times(1)).findAll();
//        verifyNoMoreInteractions(todoServiceMock);
//    }
//
//    @Test
//    public void findById_TodoEntryFound_ShouldAddTodoEntryToModelAndRenderViewTodoEntryView() throws Exception {
//        Todo found = new TodoBuilder()
//                .id(ID)
//                .description("Lorem ipsum")
//                .title("Foo")
//                .build();
//
//        when(todoServiceMock.findById(ID)).thenReturn(found);
//
//        mockMvc.perform(get("/todo/{id}", ID))
//                .andExpect(status().isOk())
//                .andExpect(view().name(TodoController.VIEW_TODO_VIEW))
//                .andExpect(forwardedUrl("/WEB-INF/jsp/todo/view.jsp"))
//                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO, hasProperty("id", is(1L))))
//                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO, hasProperty("description", is("Lorem ipsum"))))
//                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO, hasProperty("title", is("Foo"))));
//
//        verify(todoServiceMock, times(1)).findById(ID);
//        verifyNoMoreInteractions(todoServiceMock);
//    }
//
//    @Test
//    public void findById_TodoEntryNotFound_ShouldRender404View() throws Exception {
//        when(todoServiceMock.findById(ID)).thenThrow(new TodoNotFoundException(""));
//
//        mockMvc.perform(get("/todo/{id}", ID))
//                .andExpect(status().isNotFound())
//                .andExpect(view().name(net.petrikainulainen.spring.testmvc.common.controller.ErrorController.VIEW_NOT_FOUND))
//                .andExpect(forwardedUrl("/WEB-INF/jsp/error/404.jsp"));
//
//        verify(todoServiceMock, times(1)).findById(ID);
//        verifyZeroInteractions(todoServiceMock);
//    }
//
//    @Test
//    public void showUpdateTodoForm_TodoEntryFound_ShouldCreateFormObjectAndRenderUpdateTodoView() throws Exception {
//        Todo updated = new TodoBuilder()
//                .id(ID)
//                .description("Lorem ipsum")
//                .title("Foo")
//                .build();
//
//        when(todoServiceMock.findById(ID)).thenReturn(updated);
//
//        mockMvc.perform(get("/todo/update/{id}", ID))
//                .andExpect(status().isOk())
//                .andExpect(view().name(TodoController.VIEW_TODO_UPDATE))
//                .andExpect(forwardedUrl("/WEB-INF/jsp/todo/update.jsp"))
//                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO, hasProperty("id", is(1L))))
//                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO, hasProperty("description", is("Lorem ipsum"))))
//                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO, hasProperty("title", is("Foo"))));
//
//        verify(todoServiceMock, times(1)).findById(ID);
//        verifyNoMoreInteractions(todoServiceMock);
//    }
//
//    @Test
//    public void showUpdateTodoForm_TodoEntryNotFound_ShouldRender404View() throws Exception {
//        when(todoServiceMock.findById(ID)).thenThrow(new TodoNotFoundException(""));
//
//        mockMvc.perform(get("/todo/update/{id}", ID))
//                .andExpect(status().isNotFound())
//                .andExpect(view().name(net.petrikainulainen.spring.testmvc.common.controller.ErrorController.VIEW_NOT_FOUND))
//                .andExpect(forwardedUrl("/WEB-INF/jsp/error/404.jsp"));
//
//        verify(todoServiceMock, times(1)).findById(ID);
//        verifyNoMoreInteractions(todoServiceMock);
//    }
//
//    @Test
//    public void update_EmptyTodoEntry_ShouldRenderFormViewAndReturnValidationErrorForTitle() throws Exception {
//        mockMvc.perform(post("/todo/update")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .param(WebTestConstants.FORM_FIELD_ID, ID.toString())
//                .sessionAttr(TodoController.MODEL_ATTRIBUTE_TODO, new TodoDTO())
//        )
//                .andExpect(status().isOk())
//                .andExpect(view().name(TodoController.VIEW_TODO_UPDATE))
//                .andExpect(forwardedUrl("/WEB-INF/jsp/todo/update.jsp"))
//                .andExpect(model().attributeHasFieldErrors(TodoController.MODEL_ATTRIBUTE_TODO, "title"))
//                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO, hasProperty("id", is(1L))))
//                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO, hasProperty("description", isEmptyOrNullString())))
//                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO, hasProperty("title", isEmptyOrNullString())));
//
//        verifyZeroInteractions(todoServiceMock);
//    }
//
//    @Test
//    public void update_TitleAndDescriptionAreTooLong_ShouldRenderFormViewAndReturnValidationErrorsForTitleAndDescription() throws Exception {
//        String title = TestUtil.createStringWithLength(Todo.MAX_LENGTH_TITLE + 1);
//        String description = TestUtil.createStringWithLength(Todo.MAX_LENGTH_DESCRIPTION + 1);
//
//        mockMvc.perform(post("/todo/update")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .param(WebTestConstants.FORM_FIELD_DESCRIPTION, description)
//                .param(WebTestConstants.FORM_FIELD_ID, ID.toString())
//                .param(WebTestConstants.FORM_FIELD_TITLE, title)
//                .sessionAttr(TodoController.MODEL_ATTRIBUTE_TODO, new TodoDTO())
//        )
//                .andExpect(status().isOk())
//                .andExpect(view().name(TodoController.VIEW_TODO_UPDATE))
//                .andExpect(forwardedUrl("/WEB-INF/jsp/todo/update.jsp"))
//                .andExpect(model().attributeHasFieldErrors(TodoController.MODEL_ATTRIBUTE_TODO, "title"))
//                .andExpect(model().attributeHasFieldErrors(TodoController.MODEL_ATTRIBUTE_TODO, "description"))
//                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO, hasProperty("id", is(1L))))
//                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO, hasProperty("description", is(description))))
//                .andExpect(model().attribute(TodoController.MODEL_ATTRIBUTE_TODO, hasProperty("title", is(title))));
//
//        verifyZeroInteractions(todoServiceMock);
//    }
//
//    @Test
//    public void update_TodoEntryFound_ShouldUpdateTodoEntryAndRenderViewTodoEntryView() throws Exception {
//        Todo updated = new TodoBuilder()
//                .id(ID)
//                .description(DESCRIPTION)
//                .title(TITLE)
//                .build();
//
//        when(todoServiceMock.update(isA(TodoDTO.class))).thenReturn(updated);
//
//        String expectedRedirectViewPath = TestUtil.createRedirectViewPath(TodoController.REQUEST_MAPPING_TODO_VIEW);
//
//        mockMvc.perform(post("/todo/update")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .param(WebTestConstants.FORM_FIELD_DESCRIPTION, DESCRIPTION)
//                .param(WebTestConstants.FORM_FIELD_ID, ID.toString())
//                .param(WebTestConstants.FORM_FIELD_TITLE, TITLE)
//                .sessionAttr(TodoController.MODEL_ATTRIBUTE_TODO, new TodoDTO())
//        )
//                .andExpect(status().isMovedTemporarily())
//                .andExpect(view().name(expectedRedirectViewPath))
//                .andExpect(model().attribute(TodoController.PARAMETER_TODO_ID, is("1")))
//                .andExpect(flash().attribute(TodoController.FLASH_MESSAGE_KEY_FEEDBACK, is("Todo entry: title was updated.")));
//
//        ArgumentCaptor<TodoDTO> formObjectArgument = ArgumentCaptor.forClass(TodoDTO.class);
//        verify(todoServiceMock, times(1)).update(formObjectArgument.capture());
//        verifyNoMoreInteractions(todoServiceMock);
//
//        TodoDTO formObject = formObjectArgument.getValue();
//
//        assertThat(formObject.getDescription(), is(DESCRIPTION));
//        assertThat(formObject.getId(), is(ID));
//        assertThat(formObject.getTitle(), is(TITLE));
//    }
//
//    @Test
//    public void update_TodoEntryNotFound_ShouldRender404View() throws Exception {
//        when(todoServiceMock.update(isA(TodoDTO.class))).thenThrow(new TodoNotFoundException(""));
//
//        mockMvc.perform(post("/todo/update")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .param(WebTestConstants.FORM_FIELD_DESCRIPTION, DESCRIPTION)
//                .param(WebTestConstants.FORM_FIELD_ID, ID.toString())
//                .param(WebTestConstants.FORM_FIELD_TITLE, TITLE)
//                .sessionAttr(TodoController.MODEL_ATTRIBUTE_TODO, new TodoDTO())
//        )
//                .andExpect(status().isNotFound())
//                .andExpect(view().name(net.petrikainulainen.spring.testmvc.common.controller.ErrorController.VIEW_NOT_FOUND))
//                .andExpect(forwardedUrl("/WEB-INF/jsp/error/404.jsp"));
//
//        ArgumentCaptor<TodoDTO> formObjectArgument = ArgumentCaptor.forClass(TodoDTO.class);
//        verify(todoServiceMock, times(1)).update(formObjectArgument.capture());
//        verifyNoMoreInteractions(todoServiceMock);
//
//        TodoDTO formObject = formObjectArgument.getValue();
//
//        assertThat(formObject.getDescription(), is(DESCRIPTION));
//        assertThat(formObject.getId(), is(ID));
//        assertThat(formObject.getTitle(), is(TITLE));
//    }
}
