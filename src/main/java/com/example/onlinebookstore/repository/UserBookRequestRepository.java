package com.example.onlinebookstore.repository;

import com.example.onlinebookstore.entity.UserBookRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBookRequestRepository extends JpaRepository<UserBookRequest, Long> {
}
