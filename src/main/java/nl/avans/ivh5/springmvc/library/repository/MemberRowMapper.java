package nl.avans.ivh5.springmvc.library.repository;

import nl.avans.ivh5.springmvc.library.model.Member;
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
        member.setMemberID(rs.getInt("MemberID"));
        member.setFirstName(rs.getString("FirstName"));
        member.setLastName(rs.getString("LastName"));
        member.setStreet(rs.getString("Street"));
        member.setHouseNumber(rs.getString("HouseNumber"));
        member.setCity(rs.getString("City"));
        member.setPhoneNumber(rs.getString("PhoneNumber"));
        member.setEmailAddress(rs.getString("EmailAddress"));
        member.setFine(rs.getDouble("Fine"));
        return member;
    }
}
