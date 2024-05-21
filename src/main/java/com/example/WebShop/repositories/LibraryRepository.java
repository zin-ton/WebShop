package com.example.WebShop.repositories;

import com.example.WebShop.models.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LibraryRepository extends JpaRepository<Library, Long> {
    @Override
    Optional<Library> findById(Long aLong);

}
