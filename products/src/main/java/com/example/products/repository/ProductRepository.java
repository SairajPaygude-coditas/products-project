package com.example.products.repository;

import com.example.products.entity.Product;
import com.example.products.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Integer> {

    List<Product> findByUser(User user);

    // Delete a specific product by ID for a specific user
    @Modifying
    @Query("DELETE FROM Product p WHERE p.id = :productId AND p.user = :user")
    void deleteByIdAndUser(@Param("productId") int productId, @Param("user") User user);

    // Check if a product exists for a specific user
    Optional<Product> findByIdAndUser(int id, User user);
}
