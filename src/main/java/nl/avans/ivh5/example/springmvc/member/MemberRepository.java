package nl.avans.ivh5.example.springmvc.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.List;

/**
 * Created by Robin Schellius on 31-8-2016.
 */
@Repository
public class MemberRepository
{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly=true)
    public List<Member> findAll() {
        return jdbcTemplate.query("SELECT * FROM member", new MemberRowMapper());
    }

    @Transactional(readOnly=true)
    public Member findMemberById(int id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM member WHERE MembershipNumber=?",
                new Object[]{id}, new MemberRowMapper());
    }

    public Member create(final Member member) {
        final String sql = "INSERT INTO member(`FirstName`, `LastName`, `Street`, `HouseNumber`, `City`, `PhoneNumber`, `EmailAddress`, `Fine`) " +
                "VALUES(?,?,?,?,?,?,?,?,?)";

        // KeyHolder gaat de auto increment key uit de database bevatten.
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, member.getFirstName());
                ps.setString(2, member.getLastName());
                ps.setString(4, member.getHouseNumber());
                ps.setString(3, member.getStreet());
                ps.setString(5, member.getCity());
                ps.setString(6, member.getPhoneNumber());
                ps.setString(7, member.getEmailAddress());
                ps.setDouble(8, member.getFine());
                return ps;
            }
        }, holder);

        // Zet de auto increment waarde in de Member
        int newUserId = holder.getKey().intValue();
        member.setMembershipNumber(newUserId);
        return member;
    }
}