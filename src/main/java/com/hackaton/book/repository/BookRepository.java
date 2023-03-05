package com.hackaton.book.repository;

import com.hackaton.book.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import com.hackaton.book.model.Book;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIdBook(Long id);

    List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrSynopsisContainingIgnoreCase(String title, String author, String synopsis);
}
