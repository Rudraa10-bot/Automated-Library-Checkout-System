package com.library.repository;

import com.library.entity.LibraryLibraryUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibraryUserRepository extends JpaRepository<LibraryLibraryUser, Long> {
    
    Optional<LibraryLibraryUser> findByLibraryUsername(String username);
    
    Optional<LibraryLibraryUser> findByEmail(String email);
    
    boolean existsByLibraryUsername(String username);
    
    boolean existsByEmail(String email);
    
    @Query("SELECT u FROM LibraryUser u WHERE u.username = :username OR u.email = :email")
    Optional<LibraryUser> findByLibraryUsernameOrEmail(@Param("username") String username, @Param("email") String email);
}













