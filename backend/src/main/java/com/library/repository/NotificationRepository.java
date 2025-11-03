package com.library.repository;

import com.library.entity.Notification;
import com.library.entity.LibraryUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserOrderByCreatedAtDesc(LibraryUser user);
    long countByUserAndReadFalse(LibraryUser user);
}
