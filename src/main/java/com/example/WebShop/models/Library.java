package com.example.WebShop.models;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity(name = "library")
public class Library {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @OneToOne(mappedBy = "library")
    private User user;

    @OneToMany(mappedBy = "library")
    private Set<Product> products;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Library() {
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Library(Long id, User user, Set<Product> products) {
        this.id = id;
        this.user = user;
        this.products = products;
    }
}
