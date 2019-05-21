package com.vasiliska.WFlibrary.page;

import com.vasiliska.WFlibrary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BooksPageContoroller {

    private final BookService bookService;

    @Autowired
    public BooksPageContoroller(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String listPage(Model model) {
        return "index";
    }

}
