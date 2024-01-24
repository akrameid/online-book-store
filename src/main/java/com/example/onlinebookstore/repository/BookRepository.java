package com.example.onlinebookstore.repository;

import com.example.onlinebookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByName(String name);

    List<Book> findByNameContainsAndCategory(String name, String category);

    Optional<Book> findByNameLikeIgnoreCase(String name);

    List<Book> findByNameContains(String name);

    List<Book> findByCategory(String category);

    Optional<Book> findByNameAndCategory(String name, String category);

    boolean existsByName(String name);
}
