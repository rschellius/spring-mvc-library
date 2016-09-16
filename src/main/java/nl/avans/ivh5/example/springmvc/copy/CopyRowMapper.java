package nl.avans.ivh5.example.springmvc.copy;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 */
public class CopyRowMapper implements RowMapper<Copy>
{
    @Override
    public Copy mapRow(ResultSet rs, int rowNum) throws SQLException {
        Copy copy = new Copy();

        copy.setCopyID(rs.getInt("CopyID"));
        copy.setISBN(rs.getInt("BookISBN"));
        copy.setUpdatedDate(rs.getDate("UpdatedDate"));
        return copy;
    }
}