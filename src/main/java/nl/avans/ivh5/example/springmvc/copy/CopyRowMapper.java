package nl.avans.ivh5.example.springmvc.copy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 */
public class CopyRowMapper implements RowMapper<Copy>
{
    private final Logger logger = LoggerFactory.getLogger(CopyRowMapper.class);

    @Override
    public Copy mapRow(ResultSet rs, int rowNum) throws SQLException {

        logger.debug("mapRow voor CopyID " + rs.getLong("CopyID") );
        Copy copy = new Copy(rs.getLong("BookISBN"));

        //
        // DEZE CLASS IS NOG NIET COMPLEET !
        //
        // copy.setUpdatedDate(rs.getDate("UpdatedDate"));
        return copy;
    }
}