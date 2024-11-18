package com.example.products.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProductDto {

    private int id;

    @NotNull(message = "product name should not be null")
    private String name;

    @NotNull(message = "price should not be null")
    private double price;

//    @NotNull(message = " user id should not be empty or null")
//    @JsonProperty("user")
//    private int user;
}
