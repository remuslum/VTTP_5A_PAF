package sg.nus.edu.iss.vttp_5a_day21_practice.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import sg.nus.edu.iss.vttp_5a_day21_practice.model.Book;
import sg.nus.edu.iss.vttp_5a_day21_practice.service.BookService;

@Controller
@RequestMapping
public class BookController {
    
    @Autowired
    BookService bookService;

    @GetMapping
    public ModelAndView getHomePage(HttpSession httpSession){
        ModelAndView mav = new ModelAndView("home");
        httpSession.setAttribute("author", Optional.empty());
        httpSession.setAttribute("limit", Optional.empty());
        return mav;
    }

    @GetMapping("/books")
    public ModelAndView getBooks(@RequestParam MultiValueMap<String, String> params, HttpSession httpSession){
        ModelAndView mav = new ModelAndView();
        
        String author = params.getFirst("author");
        int limit = Integer.parseInt(params.getFirst("limit"));

        mav.addObject("books", bookService.getBooks(author, limit));
        mav.setViewName("books");
        return mav;
    }

    @GetMapping("/books/{asin}")
    public ModelAndView getBook(@PathVariable String asin){
        ModelAndView mav = new ModelAndView();
        Optional<Book> opt = bookService.getBook(asin);

        opt.ifPresentOrElse(
            (value) -> {
                mav.addObject("book", value);
                mav.setViewName("book");
            }, 
            () -> {
                mav.addObject("message", "Cannot find book %s".formatted(asin));
                mav.setStatus(HttpStatusCode.valueOf(404));
                mav.setViewName("notfound");
            });
        
        return mav;
    }
}
