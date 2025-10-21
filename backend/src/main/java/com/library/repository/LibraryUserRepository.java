package com.library.repository;

import com.library.entity.LibraryUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibraryUserRepository extends JpaRepository<LibraryUser, Long> {
    
    Optional<LibraryUser> findByLibraryUsername(String username);
    
    Optional<LibraryUser> findByEmail(String email);
    
    boolean existsByLibraryUsername(String username);
    
    boolean existsByEmail(String email);
    
    @Query("SELECT u FROM LibraryUser u WHERE u.username = :username OR u.email = :email")
    Optional<LibraryUser> findByLibraryUsernameOrEmail(@Param("username") String username, @Param("email") String email);
}













