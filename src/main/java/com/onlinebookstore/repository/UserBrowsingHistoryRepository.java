package com.onlinebookstore.repository;

import com.onlinebookstore.entity.UserBrowsingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserBrowsingHistoryRepository extends JpaRepository<UserBrowsingHistory, Long> {
    Optional<UserBrowsingHistory> findByReferredUser_IdAndBook_Id(Long userId, Long bookId);
}
