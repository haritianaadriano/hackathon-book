package com.hackaton.book.service;

import com.hackaton.book.model.Book;
import com.hackaton.book.model.History;
import com.hackaton.book.repository.BookRepository;
import com.hackaton.book.repository.HistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HistoryService {
    private HistoryRepository repository;
    private BookRepository bookRepository;

    //GET MAPPING
    public List<History> allHistory(){
        return repository.findAll();
    }

    public Optional<History> getHistoryById(Long id){
        return repository.findById(id);
    }
    public Optional<History> getLastBookAvailable(Long id){
        return repository.findTopByBookIdBookOrderByIdHistoryDesc(id);
    }

    //POST MAPPING
    public History insertCategory(History history){
        //Changing the date format
        LocalDate ld = LocalDate.now();
        LocalTime lt = LocalTime.now();
        LocalDateTime ldt = LocalDateTime.of(ld,lt);
        //SetDate
        history.setDateHistory(ldt);


        Long bookId = history.getBook().getIdBook();

        // setAvailable
        if (!(history.getAvailable()==null)){ //if not null then set the input value
            history.setAvailable(history.getAvailable());
        } else { //get the last status
            history.setAvailable(!getLastBookAvailable(bookId).get().getAvailable());
        }
        return repository.save(history);
    }

    //Get all histories with pagination conditioning
    public List<History> getAllHistory(Long page, Long pageSize){
        if(page != null && pageSize != null){
            Pageable pageable = PageRequest.of(Math.toIntExact(page-1), Math.toIntExact(pageSize));
            return repository.findAll(pageable).toList();
        }else if(page == null && pageSize == null){
            return repository.findAll();
        }else{
            return repository.findAll();
        }
    }

    //GET "/bookings" -> get all bookings with some condition and parameters
    //Function redirecting the request
    public List<History> redirectingRequest(Long page, Long pageSize, String bookName){
        if(bookName != null && page == null && pageSize ==null){
            return this.getHistoryByBookName(bookName);
        }else{
            return this.getAllHistory(page, pageSize);
        }
    }

    //GET bookings by books id
    public List<History> getHistoryByIdBook(Long id){
        List<History> allHistories = repository.findAll();
        List<History> historyFilteredByBook = new ArrayList<>();

        for(History history : allHistories){
            if(history.getBook().getIdBook() == id){
                historyFilteredByBook.add(history);
            }
        }
        return historyFilteredByBook;
    }

    //Get history by book-name
    public List<History> getHistoryByBookName(String bookName){
        List<History> allHistories = repository.findAll();
        List<History> historiesWithBookName = new ArrayList<>();

        for(History history : allHistories){
            if(history.getBook().getTitle() == bookName){
                historiesWithBookName.add(history);
            }
        }
        return historiesWithBookName;
    }

    //DELETE MAPPING
    public ResponseEntity<String> deleteById(Long id){
        repository.deleteById(id);
        return ResponseEntity.ok(("Booking deleted successfully!"));

    }

    //PUT Mapping
    //PUT update History by id-history
    public History putUpdate(Long id, History newHistory){
        List<History> allHistories = repository.findAll();
        List<Book> allBooks = bookRepository.findAll();

        for(Book book : allBooks){
            if(newHistory.getBook().getIdBook() == book.getIdBook()){
                newHistory.setBook(book);
            }
        }

        for(History history : allHistories){
            if(history.getIdHistory() == id){
                history.setDateHistory(newHistory.getDateHistory());
                history.setAvailable(newHistory.getAvailable());
                history.setBook(newHistory.getBook());
                repository.save(history);
            }
        }
        return newHistory;
    }

    //PATCH Mapping
    public History patchUpdateHistory(Long id, History newHistory){
        List<History> histories = repository.findAll();
        for(History history : histories){
            if(history.getIdHistory() == id){
                if (!(newHistory.getAvailable() == null)){
                    history.setAvailable(newHistory.getAvailable());
                }
                if (!(newHistory.getDateHistory() == null)){
                    history.setDateHistory(newHistory.getDateHistory());
                }
                if (!(newHistory.getBook() == null)){
                    history.setBook(newHistory.getBook());
                }
                repository.save(history);
            }
        }
        return newHistory;
    }
}