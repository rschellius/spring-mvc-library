package nl.avans.ivh5.springmvc.library.repository;

import nl.avans.ivh5.springmvc.library.model.Loan;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Robin Schellius on 3-9-2016.
 */
public class LoanRowMapper  implements RowMapper<Loan>
{
    @Override
    public Loan mapRow(ResultSet rs, int rowNum) throws SQLException {
        Loan loan = new Loan();

        loan.setLoanID(rs.getInt("LoanID"));
        loan.setLoanDate(rs.getDate("LoanDate"));
        loan.setReturnedDate(rs.getDate("ReturnedDate"));
        loan.setBookISBN(rs.getLong("ISBN"));
        loan.setBookTitle(rs.getString("Title"));
        loan.setBookAuthor(rs.getString("Author"));
        loan.setBookEdition(rs.getString("Edition"));
        loan.setCopyID(rs.getLong("CopyID"));
        loan.setLendingPeriod(rs.getInt("LendingPeriod"));
        loan.setMemberID(rs.getLong("MemberID"));
        loan.setMemberFirstName(rs.getString("FirstName"));
        loan.setMemberLastName(rs.getString("LastName"));
        return loan;
    }
}