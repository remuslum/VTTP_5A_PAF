package sg.nus.edu.iss.vttp_5a_day22_lecture.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import sg.nus.edu.iss.vttp_5a_day22_lecture.util.Queries;

@Repository
public class KindleRepo {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void getBooks(){
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(Queries.QUERY_BOOKS);
    }
}
