package nl.avans.ivh5.springmvc.library.repository;

import nl.avans.ivh5.springmvc.library.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robin Schellius on 3-9-2016.
 */
@Repository
public class BookRepository implements BookRepositoryIF {

    private final Logger logger = LoggerFactory.getLogger(BookRepository.class);;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Deze constructor wordt aangeroepen vanuit de config/PersistenceContext class.
    @Autowired
    public BookRepository(DataSource dataSource) { this.jdbcTemplate = new JdbcTemplate(dataSource); }

//    public void setDataSource(DataSource dataSource) {
//        this.jdbcTemplate = new JdbcTemplate(dataSource);
//    }

    @Transactional(readOnly=true)
    public List<Book> findAll() {
        logger.info("findAll");
        List<Book> result = new ArrayList<>();
        try {
            result = jdbcTemplate.query("SELECT * FROM view_all_books", new BookRowMapper());
        } catch(Exception ex) {
            throw ex;
        }
        return result;
    }

    @Transactional(readOnly=true)
    public List<Book> findByEAN(Long id) {
        logger.info("findByEAN ean = " + id);

        List<Book> result = jdbcTemplate.query(
                "SELECT * FROM view_all_books WHERE ISBN=?",
                new Object[]{id}, new BookRowMapper());
        if(result == null || 0 == result.size()) {
            logger.info("findByEAN returns no results");
        }
        return result;
    }

    /**
     *
     * @return
     */
    public Book create(final Book book)  {
        logger.info("create book = " + book);

        final String sql = "INSERT INTO `book` (`ISBN`, `Title`, `Author`, `ShortDescription`, `Edition`, `ImageURL`) VALUES(?,?,?,?,?,?)";

        // KeyHolder gaat de auto increment key uit de database bevatten.
        KeyHolder holder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setLong(1, book.getEAN());
                    ps.setString(2, book.getTitle());
                    ps.setString(3, book.getAuthor());
                    ps.setString(4, book.getShortDescription());
                    ps.setString(5, book.getEdition());
                    ps.setString(6, book.getImageURL());
                    return ps;
                }
            }, holder);
        } catch (Exception ex) {
            logger.warn("Boek bestond al in de database.");
        }
        return book;
    }

}
