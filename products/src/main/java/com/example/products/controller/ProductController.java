package com.example.products.controller;

import com.example.products.dto.ProductDto;
import com.example.products.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestHeader("Authorization") String authorizationHeader,
                                                    @Valid @RequestBody ProductDto productDto){

        ProductDto createdProduct = productService.createProduct(authorizationHeader,productDto);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDto> getProduct(@RequestHeader("Authorization") String authorizationHeader,
                                                 @PathVariable("id") int id){
        ProductDto createdProduct = productService.getProduct(authorizationHeader,id);
        return new ResponseEntity<>(createdProduct, HttpStatus.OK);
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<List<ProductDto>> getAllProductOfUser(@RequestHeader("Authorization") String authorizationHeader){
        List<ProductDto> products = productService.getAllProductsOFUser(authorizationHeader);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductDto> updateProduct(@RequestHeader("Authorization") String authorizationHeader,
                                                    @PathVariable("id") int id,
                                                    @Valid @RequestBody ProductDto productDto){
       ProductDto updated = productService.updateProduct(authorizationHeader,id,productDto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<ProductDto> patchProduct(@RequestHeader("Authorization") String authorizationHeader,
                                                    @PathVariable("id") int id,
                                                    @Valid @RequestBody ProductDto productDto){
        ProductDto updated = productService.patchProduct(authorizationHeader,id,productDto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteProduct(@RequestHeader("Authorization") String authorizationHeader,
                                                @PathVariable("id") int id){
        productService.deleteProduct(authorizationHeader,id);
        return new ResponseEntity<>("product deleted",HttpStatus.NO_CONTENT);
    }
}
