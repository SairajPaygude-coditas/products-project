package com.example.products.mapper;

import com.example.products.dto.UserDto;
import com.example.products.entity.Product;
import com.example.products.entity.User;
import com.example.products.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
public class UserMapper {
    ProductRepository productRepo;
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    public UserMapper(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    public UserDto mapToUserDto(User user){
        return new UserDto(user.getId(),
                user.getUsername(),
                user.getPassword()
//                user.getProducts().stream().map((p)->p.getId()).collect(Collectors.toSet())
        );
    }

    public  User mapToUser(UserDto userDto){

       Set<Product> products = new HashSet<>();


        return new User(userDto.getId(),
                userDto.getUsername(),
                encoder.encode(userDto.getPassword()),
                products
        );

    }

}
