package com.proiect.licenta.repository;

import com.proiect.licenta.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User u " +
            "SET u.enabled = true " +
            "WHERE u.email = ?1")
    int enableUser(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User u " +
            "SET u.password = ?2 " +
            "WHERE u.username = ?1")
    int updatePassword(String username, String password);
}
