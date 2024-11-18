package com.example.products.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDto {

    private int id;

    @NotNull(message = "user name should not be null")
    private String username;

    @NotNull(message = "password should not be null")
    private String password;

//    private Set<Integer> products = new HashSet<>();
}
