package com.example.onlinebookstore.repository;

import com.example.onlinebookstore.entity.UserBookRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserBookRequestRepository extends JpaRepository<UserBookRequest, Long> {
    Optional<UserBookRequest> findByBook_IdAndReferredUser_Id(Long bookId, Long userId);
}
