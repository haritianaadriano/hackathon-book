package com.hackaton.book.service;

import com.hackaton.book.model.Book;
import com.hackaton.book.model.BookAPI;
import com.hackaton.book.model.History;
import com.hackaton.book.repository.BookRepository;
import com.hackaton.book.repository.HistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookService {
    private BookRepository bookRepository;
    private HistoryRepository historyRepository;

    //GET "/books" -> return allBooks in the database
    public List<BookAPI> getAllBookAPI(){
        List<Book>allBooks = bookRepository.findAll();
        List<BookAPI> allBookAPI = new ArrayList<>();
        List<History> allHistoy = historyRepository.findAll();
        Long numberBookings = null;

        for(BookAPI bookAPI : allBookAPI){
            for(Book book : allBooks)
                bookAPI.setBook(book);
            for(History history : allHistoy){
                    if(bookAPI.getBook().getIdBook() == history.getBook().getIdBook()){
                        numberBookings++;
                    }
                }
            bookAPI.getStatus().setNumberBooking(numberBookings);
            bookAPI.getStatus().setRankBooking(0);
            allBookAPI.add(bookAPI);
        }
        return allBookAPI;
    }

    public List<Book> getAllBooks (){
        return bookRepository.findAll();
    }

    //Function getting books conditioning by page and page-size
    public List<Book> getBooksBySpecificPage(Long page, Long pageSize){
        if(page != null && pageSize != null){
            Pageable pageable = PageRequest.of(Math.toIntExact(page-1), Math.toIntExact(pageSize));
            return bookRepository.findAll(pageable).toList();
        }else {
            return bookRepository.findAll();
        }
    }

    //Function getting books conditioning by author or title or synopsis
    public List<Book> getBooksByAuthorOrTitleOrSynopsis(String author, String title, String synopsis){
        return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrSynopsisContainingIgnoreCase(title, author, synopsis);
    }

    //Function redirecting request conditioning in the client server -> GET "/books?title=" || GET "/books?author= || GET "/books?synopsis=
    public List<Book> redirectingRequest(Long page, Long pageSize, String author, String title, String synopsis){
        if(author != null || title != null || synopsis != null){
            return this.getBooksByAuthorOrTitleOrSynopsis(author, title, synopsis);
        }else{
            return this.getBooksBySpecificPage(page, pageSize);
        }
    }

    //GET "/books/{id}"
    public Optional<Book> getBookById(Long id){
        return bookRepository.findByIdBook(id);
    }

    //POST "/books" -> adding a new book
    public Book insertBook(Book book){
        bookRepository.save(book);
        return book;
    }

    //PUT "/books/{id} -> update all book attribute
    public Book putUpdateBook(Long id ,Book newBook){
        List<Book> allBooks = bookRepository.findAll();
        Book bookSearch = null;

        for(Book book : allBooks){
            if(book.getIdBook() == id){
                book.setPages(newBook.getPages());
                book.setAuthor(newBook.getAuthor());
                book.setCategory(newBook.getCategory());
                book.setSynopsis(newBook.getSynopsis());
                book.setTitle(newBook.getTitle());
                bookRepository.save(book);
                bookSearch = book;
            }
        }
        return bookSearch;
    }

    //PATCH MAPPING BOOKS
    public Book patchUpdateBook(Long id, Book newBook){
        List<Book> books = getAllBooks();
        for(Book bookInfo : books){
            if(bookInfo.getIdBook() == id){
                if (!(newBook.getTitle() == null)){
                    bookInfo.setTitle(newBook.getTitle());
                }
                if (!(newBook.getAuthor() == null)){
                    bookInfo.setAuthor(newBook.getAuthor());
                }
                if (!(newBook.getPages() == null)){
                    bookInfo.setPages(newBook.getPages());
                }
                if (!(newBook.getSynopsis() == null)){
                    bookInfo.setSynopsis(newBook.getSynopsis());
                }
                if (!(newBook.getCategory() == null)){
                    bookInfo.setCategory(newBook.getCategory());
                }
                bookRepository.save(bookInfo);
            }
        }
        return newBook;
    }

    //DELETE "/books/{id} -> Delete book by id-book
    public void deleteBookById(Long id){
        bookRepository.deleteById(id);
    }

    //GET "/books/count -> Get books count in library
    public int bookCount(){
        List<Book> allBooks = bookRepository.findAll();
        int bookCount = allBooks.size();
        return bookCount;
    }

    //GET "/categories/{id_category}/books" -> get all books conditioning by id-category
    public List<Book> bookByCategory(Long id) {
        List<Book> allBooks = bookRepository.findAll();
        List<Book> bookFiltered = new ArrayList<>();
        for(Book book : allBooks){
            if(book.getCategory().getIdCategory() == id){
                bookFiltered.add(book);
            }
        }
        return bookFiltered;
    }
}
