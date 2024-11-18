package com.example.products.repository;

import com.example.products.entity.Product;
import com.example.products.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

//    @Transactional(readOnly = true)
//    @Query("SELECT p from product p WHERE p.user.id = :id")
//    Set<Product> fetchProductsOfUserById(@Param("userId") int id);
}
