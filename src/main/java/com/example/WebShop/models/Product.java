package com.example.WebShop.models;

import jakarta.persistence.*;

@Entity(name = "products")
public class Product {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "library_id", nullable = true)
    private Library library;


    public Product() {
    }

    public Product(Long id, String name, String description, Library library) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.library = library;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }
}
