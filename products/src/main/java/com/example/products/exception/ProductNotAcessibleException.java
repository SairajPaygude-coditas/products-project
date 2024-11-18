package com.example.products.exception;

public class ProductNotAcessibleException extends RuntimeException {
    public ProductNotAcessibleException(String message) {
        super(message);
    }
}
