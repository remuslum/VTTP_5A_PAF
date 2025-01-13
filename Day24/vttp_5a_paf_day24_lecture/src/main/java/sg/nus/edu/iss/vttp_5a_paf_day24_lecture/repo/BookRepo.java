package sg.nus.edu.iss.vttp_5a_paf_day24_lecture.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import sg.nus.edu.iss.vttp_5a_paf_day24_lecture.model.Book;
import sg.nus.edu.iss.vttp_5a_paf_day24_lecture.model.exception.BookNotFoundException;
import sg.nus.edu.iss.vttp_5a_paf_day24_lecture.util.Queries;

@Repository
public class BookRepo {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int insertBook(Book book){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = (Connection con) -> {
            PreparedStatement ps = con.prepareStatement(Queries.QUERY_TO_CREATE_BOOK, new String[]{"id"});
            ps.setString(1, book.getTitle());
            ps.setInt(2, book.getQuantity());
            return ps;
        };
        jdbcTemplate.update(psc, keyHolder);
        int bookId = keyHolder.getKey().intValue();
        return bookId;
    }

    public List<Book> getAllBooks(){
        List<Book> books = jdbcTemplate.query(Queries.QUERY_TO_GET_BOOKS, BeanPropertyRowMapper.newInstance(Book.class));
        return books;
    }

    public Book getBookById(int bookId){
        Optional<Book> bookOptional = Optional.ofNullable(jdbcTemplate.queryForObject(Queries.QUERY_TO_GET_BOOK_BY_ID, BeanPropertyRowMapper.newInstance(Book.class), bookId));
        return bookOptional
        .map((value) -> value)
        .orElseThrow(() -> {
            return new BookNotFoundException("Book does not exist in our database");
        });
    }

    public boolean updateBook(Book book){
        return jdbcTemplate.update(Queries.QUERY_TO_UPDATE_BOOK_BY_ID, book.getTitle(), book.getQuantity(), book.getId()) > 0;
    }

    public boolean updateBookStatus(Book book){
        return jdbcTemplate.update(Queries.QUERY_TO_UPDATE_BOOK_STATUS_BY_ID, book.isActive(), book.getId())> 0;
    }
}
