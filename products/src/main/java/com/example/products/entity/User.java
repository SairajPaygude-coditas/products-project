package com.example.products.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_table")
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", nullable = false, unique = true)
    @NotNull(message = "username should not be null")
    private String username;

    @Column(name = "password", nullable = false)
    @NotNull(message = "password should not be null")
    private String password;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "user", orphanRemoval = true)
    private Set<Product> products = new HashSet<>();




    public void addProduct(Product product){
        this.products.add(product);
        product.setUser(this);
    }

    public void removeProduct(Product product){
        product.setUser(null);
        this.products.remove(product);
    }

    public void removeAllProduct(){
        this.products.forEach(product -> {
            product.setUser(null);
        });
        this.products.clear();
    }

}
