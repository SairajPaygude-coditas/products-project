package com.example.products.mapper;

import com.example.products.dto.ProductDto;
import com.example.products.entity.Product;
import com.example.products.entity.User;
import com.example.products.exception.ResourceNotFoundException;
import com.example.products.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductMapper {
    private UserRepository userRepo;


    public ProductDto mapToProductDto(Product product){
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getPrice()
//                product.getUser().getId()
        );
    }

    public Product mapToProduct(ProductDto productDto, User user){

        return new Product(
                productDto.getId(),
                productDto.getName(),
                productDto.getPrice(),
                user
        );
    }

}
