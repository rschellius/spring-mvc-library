package nl.avans.ivh5.springmvc.library.repository;

import com.mysql.jdbc.PreparedStatement;
import nl.avans.ivh5.springmvc.library.model.Book;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Deze class bevat testcases voor het testen van de BookRepository class.
 * Omdat we alleen de repository willen testen moeten we onafhankelijk zijn van de omringende lagen. We willen
 * dus eigenlijk niet naar de data laag om boeken op te halen, maar we willen dat simuleren - of mocken.
 *
 * Mockito is een framework dat het mogelijk maakt om stubs (of mocks) van bestaande classes te maken, en
 * te bepalen hoe die mocks reageren op aanroepen, bv uit de BookService. Zo hebben we controle over de
 * omliggende classen en kunnen we de functionaliteit van de BookService geïsoleerd testen. Dit is dus
 * feitelijk een unit test.
 */
@RunWith(MockitoJUnitRunner.class)
public class BookRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(BookRepositoryTest.class);

    // De volgende parameters worden met Mockito gemockt.
    // We gaan verderop hun gedrag definiëren.
    @Mock
    private Book mockBook;

    @Mock
    JdbcTemplate mockJdbcTemplate;

    @Mock
    DataSource mockDataSource;

    @Mock
    Connection mockConn;

    @Mock
    PreparedStatement mockPreparedStmnt;

    @Mock
    ResultSet mockResultSet;

    @InjectMocks
    private BookRepository bookRepository;

    @Autowired
    private ApplicationContext appContext;

    private List<Book> bookArrayList;
    private Long ean = 1111L;

    public BookRepositoryTest() {}

    @Before
    public void setUp() throws SQLException {
        logger.info("---- setUp ----");

        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {
        logger.info("---- tearDown ----");
    }

    @Ignore
    @Test
    public void testCreateWithNoExceptions() throws SQLException {
        logger.info("---- testCreateWithNoExceptions ----");

        Long ean = 1234L;
        String title = "Titel van het boek";
        String author = "Author Name";
        String sql = "SELECT * FROM view_all_books WHERE ISBN=?";
        List<Book> books = new ArrayList<Book>();
        books.add(new Book.Builder(ean, title, author).build());

        mockJdbcTemplate = mock(JdbcTemplate.class);
        mockDataSource = mock(DataSource.class);
        mockPreparedStmnt = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);
        mockJdbcTemplate.setDataSource(mockDataSource);

//        when(mockConn.createStatement()).thenReturn(mockPreparedStmnt);
        when(mockPreparedStmnt.executeQuery(anyString())).thenReturn(resultSet);
        when(mockJdbcTemplate.query(sql, new Object[]{ean}, new BookRowMapper() )).thenReturn(books);
        when(mockJdbcTemplate.query(anyString(), new BookRowMapper() )).thenReturn(books);


        BookRepository bookRepository = new BookRepository(appContext.getBean(DriverManagerDataSource.class));
        // bookRepository.setDataSource(mockDataSource);
        List<Book> result = bookRepository.findByEAN(ean);

        Assert.assertEquals(result, books);
    }

    @Ignore
    @Test(expected = DataAccessException.class)
    public void testCreateWithPreparedStmntException() throws SQLException {
        logger.info("---- testCreateWithPreparedStmntException ----");

        //mock
//        when(mockConn.prepareStatement(anyString(), anyInt())).thenThrow(new SQLException());
        Long ean = 1234L;
        String title = "Titel van het boek";
        String author = "Author Name";
        String sql = "SELECT * FROM view_all_books WHERE ISBN=?";
        List<Book> books = new ArrayList<Book>();
        books.add(new Book.Builder(ean, title, author).build());

        when(mockJdbcTemplate.query(sql, new Object[]{ean}, new BookRowMapper() )).thenReturn(books);

        try {
            BookRepository bookRepository = new BookRepository(appContext.getBean(DriverManagerDataSource.class));
            List<Book> result = bookRepository.findByEAN(ean);
        } catch (DataAccessException ex) {
            logger.info("---- Exception as expexted ----");
//            //verify and assert
//            verify(mockConn, times(1)).prepareStatement(anyString(), anyInt());
//            verify(mockPreparedStmnt, times(0)).setString(anyInt(), anyString());
//            verify(mockPreparedStmnt, times(0)).execute();
//            verify(mockConn, times(0)).commit();
//            verify(mockResultSet, times(0)).next();
//            verify(mockResultSet, times(0)).getInt(Fields.GENERATED_KEYS);
//            throw se;
        }

    }
}