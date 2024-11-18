package com.example.products.service.impl;

import com.example.products.controller.ProductController;
import com.example.products.dto.ProductDto;
import com.example.products.entity.Product;
import com.example.products.entity.User;
import com.example.products.exception.ProductNotAcessibleException;
import com.example.products.exception.ResourceNotFoundException;
import com.example.products.mapper.ProductMapper;
import com.example.products.repository.ProductRepository;
import com.example.products.repository.UserRepository;
import com.example.products.service.JwtService;
import com.example.products.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    ProductMapper productMapper;
    ProductRepository productRepo;
    UserRepository userRepo;
    JwtService jwtService;

    @Override
    public ProductDto createProduct(String token, ProductDto productDto) {
        String username = jwtService.extractUsername(token);
        User user = userRepo.findByUsername(username)
                .orElseThrow(()->new ResourceNotFoundException("User not found"));

        Product product = productMapper.mapToProduct(productDto,user);
//        user.addProduct(product);
//        userRepo.save(user);
        Product savedProduct = productRepo.save(product);
        return productMapper.mapToProductDto(savedProduct);
    }

    @Override
    public ProductDto getProduct(String token,int id) {
        String username = jwtService.extractUsername(token);
        User user = userRepo.findByUsername(username)
                .orElseThrow(()->new ResourceNotFoundException("User not found"));
        Product product = productRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Product not found"));

        ProductDto productDto;
        if(user.getId() == product.getUser().getId())
             productDto = productMapper.mapToProductDto(product);
        else {
            productDto = null;
            throw new ProductNotAcessibleException("cannot access this product");

        }

        return productDto;
    }

    @Override
    public List<ProductDto> getAllProductsOFUser(String token) {
        String username = jwtService.extractUsername(token);
        User user = userRepo.findByUsername(username)
                .orElseThrow(()->new ResourceNotFoundException("User not found"));
        List<Product> products = productRepo.findByUser(user);
        return products.stream().map(e->productMapper.mapToProductDto(e)).collect(Collectors.toList());
    }

    @Override
    public ProductDto updateProduct(String token, int id, ProductDto productDto) {
        String username = jwtService.extractUsername(token);
        User user = userRepo.findByUsername(username)
                .orElseThrow(()->new ResourceNotFoundException("User not found"));
        Product product = productRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Product not found"));

        ProductDto updated;
        if(user.getId() == product.getUser().getId()) {
            product.setPrice(productDto.getPrice());
            product.setName(productDto.getName());
            productRepo.save(product);
            updated = productMapper.mapToProductDto(product);
        }
        else {
            updated =   null;
            throw new ProductNotAcessibleException("cannot access this product");
        }

        return updated;
    }

    @Override
    public ProductDto patchProduct(int id, ProductDto productDto) {
        return null;
    }

    @Transactional
    @Override
    public void deleteProduct(String token, int id) {
        String username = jwtService.extractUsername(token);
        User user = userRepo.findByUsername(username)
                .orElseThrow(()->new ResourceNotFoundException("User not found"));
        Product product = productRepo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Product not found"));

        if(user.getId() == product.getUser().getId())
            productRepo.deleteByIdAndUser(id,product.getUser());
        else {
            throw new ProductNotAcessibleException("cannot access this product");
        }
    }
}
