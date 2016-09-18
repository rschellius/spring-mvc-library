package nl.avans.ivh5.example.springmvc.member;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final Logger logger = LoggerFactory.getLogger(MemberRepository.class);;

    /**
     *
     * @return
     */
    @Transactional(readOnly=true)
    public List<Member> findAll() {
        logger.debug("findAll");
        return jdbcTemplate.query("SELECT * FROM member", new MemberRowMapper());
    }

    /**
     *
     * @param id
     * @return
     */
    @Transactional(readOnly=true)
    public Member findMemberById(int id) {
        logger.debug("findMemberById");
        return jdbcTemplate.queryForObject(
                "SELECT * FROM member WHERE MemberID=?",
                new Object[]{id}, new MemberRowMapper());
    }

    /**
     *
     * @param member
     * @return
     */
    public Member create(final Member member) {

        logger.debug("create");
        final String sql = "INSERT INTO member(`FirstName`, `LastName`, `Street`, `HouseNumber`, `City`, `PhoneNumber`, `EmailAddress`, `Fine`) " +
                "VALUES(?,?,?,?,?,?,?,?)";

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
                ps.setDouble(8, 0.0);
                return ps;
            }
        }, holder);

        // Zet de auto increment waarde in de Member
        int newMemberId = holder.getKey().intValue();
        member.setMemberID(newMemberId);
        return member;
    }

    public void deleteMemberById(int id) {
        logger.debug("deleteMemberById");
        jdbcTemplate.update("DELETE FROM member WHERE MemberID=?", new Object[]{id});
    }

}