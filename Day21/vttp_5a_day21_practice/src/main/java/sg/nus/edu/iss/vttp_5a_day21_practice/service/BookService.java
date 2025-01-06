package sg.nus.edu.iss.vttp_5a_day21_practice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.edu.iss.vttp_5a_day21_practice.model.Book;
import sg.nus.edu.iss.vttp_5a_day21_practice.repo.BookRepo;

@Service
public class BookService {
    
    @Autowired
    BookRepo bookRepo;

    public List<Book> getBooks(String author, int limit){
        return bookRepo.getBooks(author, limit);
    }

    public Book getBook(String asin){
        return bookRepo.getBook(asin);
    }
}
