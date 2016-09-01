package nl.avans.ivh5.example.springmvc.member;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Robin Schellius on 31-8-2016.
 */
class MemberRowMapper implements RowMapper<Member>
{
    @Override
    public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
        Member member = new Member();
        member.setId(rs.getInt("id"));
        member.setName(rs.getString("name"));
        member.setEmail(rs.getString("email"));
        return member;
    }
}
