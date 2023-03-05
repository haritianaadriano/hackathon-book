package com.hackaton.book.repository;

import com.hackaton.book.model.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HistoryRepository extends JpaRepository<History, Long> {
    Optional<History> findByIdHistory(Long id);
    Optional<History> findByAvailable(Long id);
    Optional<History> findTopByBookIdBookOrderByIdHistoryDesc(Long id);
}
