package nl.avans.ivh5.example.springmvc.copy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by Robin Schellius on 3-9-2016.
 */
@Repository
public class CopyRepository {

    private final Logger logger = LoggerFactory.getLogger(CopyRepository.class);;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     *
     * @return
     */
    @Transactional(readOnly=true)
    public List<Copy> findAll() {
        return jdbcTemplate.query("SELECT * FROM copy", new CopyRowMapper());
    }

    /**
     *
     * @param id
     * @return
     */
    @Transactional(readOnly=true)
    public List<Copy> findById(int id) {
        return jdbcTemplate.query(
                "SELECT * FROM copy WHERE CopyID=?",
                new Object[]{id}, new CopyRowMapper());
    }

    /**
     *
     * @param copy
     * @return
     */
    public Copy create(final Copy copy) {
        final String sql = "INSERT INTO copy(`LendingPeriod`, `BookISBN`) VALUES(5,?)";

        logger.debug("create copy voor ISBN " + copy.getISBN());

        // KeyHolder gaat de auto increment key uit de database bevatten.
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, copy.getISBN());
                return ps;
            }
        }, holder);

        // Zet de auto increment waarde in de Member
        int newCopyId = holder.getKey().intValue();
        copy.setCopyID(newCopyId);
        return copy;
    }

}
