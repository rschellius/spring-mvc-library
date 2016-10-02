package nl.avans.ivh5.springmvc.library.repository;

import nl.avans.ivh5.springmvc.library.model.Copy;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Robin Schellius on 3-9-2016.
 */
@Repository
public class CopyRepository {

    private final Logger logger = LoggerFactory.getLogger(CopyRepository.class);

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Deze constructor wordt aangeroepen vanuit de config/PersistenceContext class.
    public CopyRepository(DataSource dataSource) { this.jdbcTemplate = new JdbcTemplate(dataSource); }

    /**
     *
     * @return
     */
    @Transactional(readOnly=true)
    public List<Copy> findAll() {
        return jdbcTemplate.query("SELECT * FROM service", new CopyRowMapper());
    }

    /**
     * Haal een lijst van gegevens over uitleningen van een boek.
     * Een uitlening is een service van een boek die ofwel uitgeleend is maar nog
     * niet teruggebracht (ReturnedDate is NULL), of een service die is teruggebracht
     * (de meest recente ReturnedDate van dat boek).
     *
     * @param ean
     * @return
     */
    @Transactional(readOnly=true)
    public List<Copy> findLendingInfoByBookEAN(Long ean) {

        logger.debug("findLendingInfoByBookEAN " + ean);

        //
        // Querystring met named parameters.
        //
        final String sql = "SELECT * FROM `view_available_copies` WHERE `ISBN` = '" + ean + "';";

        // Map de de actuele waarde van ean op de named parameter.
//        SqlParameterSource namedParameters = new MapSqlParameterSource("isbn", ean);

        //
        // queryForList geeft een lijst (array) van key-value mappings terug.
        // Map heeft de vorm [{name=Bob, id=1}, {name=Mary, id=2}].
        // We moeten dus per entry in de map een Copy aanmaken, de waarden uit de map lezen,
        // en die in onze service zetten.
        // Zie http://docs.spring.io/spring/docs/current/spring-framework-reference/html/jdbc.html
        //
        List<Copy> copies = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        logger.debug("Found " + rows.size() + " copies");
        for (Map row : rows) {
            Copy copy = new Copy(ean);

            // Deze attributen worden door de database gegenereerd.
            // Zijn dus altijd aanwezig (hoeven dus niet op null te checken).
            copy.setCopyID((Long)row.get("CopyID"));
            copy.setLendingPeriod((Long)row.get("LendingPeriod"));

            if(row.get("MemberID") == null)
                copy.setMemberID(null);
            else copy.setMemberID((Long)row.get("MemberID"));

            if(row.get("FirstName") == null)
                copy.setFirstName(null);
            else copy.setFirstName((String) row.get("FirstName"));

            if(row.get("LastName") == null)
                copy.setLastName(null);
            else copy.setLastName((String) row.get("LastName"));

            if(row.get("LoanDate") == null)
                copy.setLoanDate(null);
            else copy.setLoanDate((Date) row.get("LoanDate"));

            if(row.get("ReturnedDate") == null)
                copy.setReturnedDate(null);
            else copy.setReturnedDate((Date) row.get("ReturnedDate"));

            if(row.get("LoanID") == null)
                copy.setLoanID(null);
            else copy.setLoanID((Long) row.get("LoanID"));

            if(row.get("Available") == null)
                copy.setAvailable(0);
            else copy.setAvailable((int) row.get("Available"));

            logger.debug(copy.toString());
            copies.add(copy);
        }

        return copies;
    }

    /**
     *
     * @param copy
     * @return
     */
    public Copy create(final Copy copy) {
        final String sql = "INSERT INTO copy(`LendingPeriod`, `BookISBN`) VALUES(5,?)";

        logger.debug("create service voor ISBN " + copy.getISBN());

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

        // Zet de auto increment waarde in de Copy
        Long newCopyId = holder.getKey().longValue();
        copy.setCopyID(newCopyId);
        return copy;
    }

}
