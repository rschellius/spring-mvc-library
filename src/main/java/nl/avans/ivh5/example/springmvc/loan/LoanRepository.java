package nl.avans.ivh5.example.springmvc.loan;

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
public class LoanRepository implements LoanRepositoryIF {

    private static final Logger logger = LoggerFactory.getLogger(LoanRepository.class);

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private JdbcTemplate jdbcTemplate;

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
    public Loan create(final Loan loan) {

        logger.debug("create loan copyID " + loan.getCopyID() + ", member " + loan.getMemberID());

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

        // Zet de auto increment waarde in de Member
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
