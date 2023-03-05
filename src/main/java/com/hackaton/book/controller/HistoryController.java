package com.hackaton.book.controller;

import com.hackaton.book.model.History;
import com.hackaton.book.service.HistoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@AllArgsConstructor
public class HistoryController {
    private HistoryService service;

    //GET MAPPING
    @GetMapping("/bookings")
    public List<History> getAllHistory(
            @RequestParam(name = "page", required = false)Long page,
            @RequestParam(name = "page_size", required = false)Long pageSize,
            @RequestParam(name = "bookname", required = false)String bookname
    ){
        return service.redirectingRequest(page, pageSize, bookname);
    }
    @GetMapping("/bookings/{id}")
    public Optional<History> getCategoryById(@PathVariable Long id){
        return service.getHistoryById(id);
    }
    @GetMapping("/available/{id}")
    public Optional<History> getAvailable(@PathVariable Long id){
        return service.getLastBookAvailable(id);
    }

    @GetMapping("/books/{id_Book}/bookings")
    public List<History> getHistoriesByBookId(@PathVariable(name = "id_Book")Long id){
        return service.getHistoryByIdBook(id);
    }

    //POST MAPPING
    @PostMapping("/bookings")
    public History postHistory(@RequestBody History history){
        return service.insertCategory(history);
    }

    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<String> deleteHistoryById(@PathVariable(name = "id")Long id){
        return service.deleteById(id);
    }

    //PUT MAPPING
    @PutMapping("/bookings/{id}")
    public History putUpdateHistory(
            @PathVariable(name = "id")Long id,
            @RequestBody History newHistory
    ){
        return service.putUpdate(id, newHistory);
    }

    //PATCH MAPPING
    @PatchMapping("/bookings/{id}")
    public History patchUpdateHistory(
            @PathVariable(name = "id")Long id,
            @RequestBody History newHistory
    ){
        return  service.patchUpdateHistory(id, newHistory);
    }
}
