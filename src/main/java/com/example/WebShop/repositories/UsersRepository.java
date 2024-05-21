package com.example.WebShop.repositories;


import com.example.WebShop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String username);
    @Query("SELECT u FROM users u WHERE u.login like ?1")
    List<User> findByLoginBeginWith(String login);
    Optional<User> findById(Long id);
}
