package sg.nus.edu.iss.vttp_5a_day21_practice.repo;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import sg.nus.edu.iss.vttp_5a_day21_practice.model.Book;
import sg.nus.edu.iss.vttp_5a_day21_practice.util.Queries;

@Repository
public class BookRepo {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Book> getBooks(String author, int limit){
        // Another way to do it: 
        // String s = "%%%s%%".formatted(author)
        String authorWithPercent = "%%%s%%".formatted(author);

        // String authorWithPercent = new StringBuilder().append("%").append(author).append("%").toString();
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(Queries.QUERY_STRING, authorWithPercent, limit);
        List<Book> bookList = new LinkedList<>();
        while(rowSet.next()){
            bookList.add(Book.createBook(rowSet));
        }
        return bookList;
    }

    public Optional<Book> getBook(String asin){
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(Queries.QUERY_BOOK_STRING, asin);
        if (!rowSet.next()){
            return Optional.empty();
        } 
        return Optional.of(Book.createBook(rowSet));
    }
    
}
