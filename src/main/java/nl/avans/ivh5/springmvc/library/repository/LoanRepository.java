package nl.avans.ivh5.springmvc.library.repository;

import nl.avans.ivh5.springmvc.library.model.Loan;
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
import java.util.List;

/**
 * Created by Robin Schellius on 3-9-2016.
 */
@Repository
public class LoanRepository implements LoanRepositoryIF {

    private static final Logger logger = LoggerFactory.getLogger(LoanRepository.class);

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Deze constructor wordt aangeroepen vanuit de config/PersistenceContext class.
    public LoanRepository(DataSource dataSource) { this.jdbcTemplate = new JdbcTemplate(dataSource); }

    @Transactional(readOnly=true)
    public List<Loan> findAll() {
        return jdbcTemplate.query("SELECT * FROM view_all_loans", new LoanRowMapper());
    }

    /**
     *
     * @param id
     * @return
     */
    @Transactional(readOnly=true)
    public List<Loan> findAllByMemberId(int id) {
        return jdbcTemplate.query(
                "SELECT * FROM view_all_loans WHERE (ReturnedDate IS NULL) AND (MemberID=?)",
                new Object[]{id}, new LoanRowMapper());
    }

    /**
     *
     * @param ean Het ISBN nummer van het boek waarvan we loaninfo ophalen.
     * @return
     */
    @Transactional(readOnly=true)
    public List<Loan> findAllByBookEAN(Long ean) {
        return jdbcTemplate.query(
                "SELECT * FROM view_all_loans WHERE (ReturnedDate IS NULL) AND (ISBN=?)",
                new Object[]{ean}, new LoanRowMapper());
    }

    /**
     *
     * @param id
     * @return
     */
    @Transactional(readOnly=true)
    public Loan findLoanById(int id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM view_all_loans WHERE LoanID=?",
                new Object[]{id}, new LoanRowMapper());
    }

    /**
     *
     * @param loan
     * @return
     */
    public Loan create(final Loan loan) throws Exception {

        logger.debug("create - member = " + loan.getMemberID() + ", copyID = " + loan.getCopyID());

        final String sql = "INSERT INTO loan(`MemberID`, `CopyID`) VALUES(?,?)";

        // KeyHolder gaat de auto increment key uit de database bevatten.
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.NO_GENERATED_KEYS);
                ps.setLong(1, loan.getMemberID());
                ps.setLong(2, loan.getCopyID());
                return ps;
            }
        }, holder);
        return loan;
    }

    /**
     *
     * @param loan
     * @return
     */
    public void finish(final Loan loan) {
        logger.debug("finish LoanID = " + loan.getLoanID());
        final String sql = "UPDATE loan SET `ReturnedDate` = CURDATE() WHERE `LoanID` = ?";
        jdbcTemplate.update(sql, loan.getLoanID());
    }

}
