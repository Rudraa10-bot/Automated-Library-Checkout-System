package com.library.repository;

import com.library.entity.LibraryUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<LibraryUser, Long> {
    
    Optional<LibraryUser> findByUsername(String username);
    
    Optional<LibraryUser> findByEmail(String email);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    @Query("SELECT u FROM LibraryUser u WHERE u.username = :username")
    Optional<LibraryUser> findUserByUsername(@Param("username") String username);
    
    @Query("SELECT u FROM LibraryUser u WHERE u.email = :email")
    Optional<LibraryUser> findUserByEmail(@Param("email") String email);
}
