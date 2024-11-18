package com.example.products.service;

import com.example.products.dto.ProductDto;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

public interface ProductService {
    ProductDto createProduct(String token, ProductDto productDto);

    ProductDto getProduct(String token,int id);

    List<ProductDto> getAllProductsOFUser(String token);

    ProductDto updateProduct(String token,int id,ProductDto productDto);

    ProductDto patchProduct(int id,ProductDto productDto);

    void deleteProduct(String token,int id);
}
