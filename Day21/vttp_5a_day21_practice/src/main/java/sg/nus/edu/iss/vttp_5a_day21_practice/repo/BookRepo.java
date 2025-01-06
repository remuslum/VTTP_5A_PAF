package sg.nus.edu.iss.vttp_5a_day21_practice.repo;

import java.util.LinkedList;
import java.util.List;

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
        String authorWithPercent = new StringBuilder().append("%").append(author).append("%").toString();
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(Queries.QUERY_STRING, authorWithPercent, limit);
        List<Book> bookList = new LinkedList<>();
        while(rowSet.next()){
            bookList.add(Book.createBook(rowSet));
        }
        return bookList;
    }

    public Book getBook(String asin){
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(Queries.QUERY_BOOK_STRING, asin);
        List<Book> bookList = new LinkedList<>();
        while(rowSet.next()){
            bookList.add(Book.createBook(rowSet));
        }
        return bookList.get(0);
    }
    
}
