package sg.nus.edu.iss.vttp_5a_day21_practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import sg.nus.edu.iss.vttp_5a_day21_practice.service.BookService;

@Controller
@RequestMapping
public class BookController {
    
    @Autowired
    BookService bookService;

    @GetMapping("/books")
    public ModelAndView getBooks(@RequestParam MultiValueMap<String, String> params){
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
        mav.addObject("book", bookService.getBook(asin));
        mav.setViewName("book");
        return mav;
    }
}
