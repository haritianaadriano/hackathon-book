package com.hackaton.book.controller;

import com.hackaton.book.model.Book;
import com.hackaton.book.model.BookAPI;
import com.hackaton.book.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@AllArgsConstructor
public class BookController {
    private BookService bookService;

    @GetMapping("/books")
    public List<BookAPI> getAllBook(
            @RequestParam(name = "page", required = false)Long page,
            @RequestParam(name = "page_size", required = false)Long pageSize,
            @RequestParam(name = "title", required = false)String title,
            @RequestParam(name = "author", required = false)String author,
            @RequestParam(name = "synopsis", required = false)String synopsis
    ){
        return bookService.getAllBookAPI();
    }

    @GetMapping("/books/{id}")
    public Optional<Book> getBookById(@PathVariable(name = "id")Long id){
        return bookService.getBookById(id);
    }

    @GetMapping("/books/count")
    public int getBooksCount(){
        return bookService.bookCount();
    }

    @GetMapping("/categories/{id_category}/books")
    public List<Book> bookByIdCategroy(@PathVariable(name = "id_category") Long idCategory){
        return bookService.bookByCategory(idCategory);
    }

    @PostMapping("/books")
    public Book insertBook(@RequestBody Book book){
        return bookService.insertBook(book);
    }

    @PutMapping("books/{id}")
    public Book updateBook(
            @PathVariable(name = "id")Long id,
            @RequestBody Book newBook
    ){
        return bookService.putUpdateBook(id, newBook);
    }

    @PatchMapping("books/{id}")
    public Book patchUpdateBook(
            @PathVariable(name = "id")Long id,
            @RequestBody Book newBook
    ){
        return bookService.patchUpdateBook(id, newBook);
    }

    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable Long id){
        bookService.deleteBookById(id);
    }
}
