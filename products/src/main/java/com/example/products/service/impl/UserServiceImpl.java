package com.example.products.service.impl;

import com.example.products.dto.AuthenticationRequest;
import com.example.products.dto.AuthenticationResponse;
import com.example.products.dto.UserDto;
import com.example.products.entity.Product;
import com.example.products.entity.RefreshToken;
import com.example.products.entity.User;
import com.example.products.exception.ResourceNotFoundException;
import com.example.products.exception.UsernameNotUniqueException;
import com.example.products.mapper.UserMapper;
import com.example.products.repository.ProductRepository;
import com.example.products.repository.RefreshTokenRepository;
import com.example.products.repository.UserRepository;
import com.example.products.service.JwtService;
import com.example.products.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    UserRepository repo;
    UserMapper userMapper;
    ProductRepository productRepo;
    JwtService jwtService;
    RefreshTokenRepository refreshTokenRepository;
    AuthenticationManager authManager;


    @Override
    public UserDto createUser(UserDto userDto) {
        if(repo.existsByUsername(userDto.getUsername())){
            throw new UsernameNotUniqueException(
                    "Username "+userDto.getUsername()+" already exists");
        }
        User user = userMapper.mapToUser(userDto);
        User savedUser = repo.save(user);
        return userMapper.mapToUserDto(savedUser);
    }

    @Override
    public UserDto getUser(int id) {
        User user = repo.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("User not found"));
        return userMapper.mapToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = repo.findAll();
        return users.stream().map(e->userMapper.mapToUserDto(e)).collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(int id, UserDto updatedUser) {
        User user = repo.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found"));
        user.setUsername(updatedUser.getUsername());
        user.setPassword(user.getPassword());
//        user.setProducts(updatedUser.getProducts().stream().map(p->productRepo.findById(p)
//                        .orElseThrow(()->new ResourceNotFoundException("Permission not found")))
//                .collect(Collectors.toSet()));

        User update = repo.save(user);
        return userMapper.mapToUserDto(update);
    }

    @Override
    public UserDto patchUser(int id, UserDto patchedUser) {
        User user = repo.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found"));
        if(patchedUser.getUsername() != null)
            user.setUsername(patchedUser.getUsername());
        if(patchedUser.getPassword() != null)
            user.setPassword(patchedUser.getPassword());
//        if(patchedUser.getProducts() != null)
//            user.setProducts(patchedUser.getProducts().stream().map(p->productRepo.findById(p)
//                            .orElseThrow(()->new ResourceNotFoundException("Permission not found")))
//                    .collect(Collectors.toSet()));

        User patch = repo.save(user);
        return userMapper.mapToUserDto(patch);
    }

    @Override
    public void deleteUser(int id) {
        User user = repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found"));
        List<Product> products = productRepo.findByUser(user);
        products.forEach(p->p.setUser(null));
        productRepo.saveAll(products);
        user.getProducts().clear();
        RefreshToken refreshToken=refreshTokenRepository.findByUserId(id).orElseThrow(()->
                new ResourceNotFoundException("refresh token not found")
        );
        refreshTokenRepository.delete(refreshToken);
        repo.save(user);
        repo.deleteById(id);
    }

    @Override
    public AuthenticationResponse verify(AuthenticationRequest request) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),request.getPassword())
        );

        if (authentication.isAuthenticated()) {
            String accessToken =  jwtService.generateToken(request.getUsername());
            RefreshToken refreshToken = jwtService.createRefreshToken(request.getUsername());
            return  new AuthenticationResponse(accessToken,refreshToken.getToken());


        }
        return new AuthenticationResponse("failed","failed");
    }

    @Override
    public String refresh(String refreshToken) {
//        if (jwtService.isTokenExpired(refreshToken)) {
//            throw new RuntimeException("Refresh token expired");
//        }
//
//        String username = jwtService.extractUsername(refreshToken);
//
//        return jwtService.generateToken(username);

        return refreshTokenRepository.findById(refreshToken)
                .map(jwtService::verifyRefreshTokenExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    return jwtService.generateToken(user.getUsername());
                }).orElseThrow(() -> new RuntimeException(
                        "Refresh token is not in database!"));

    }

    @Override
    public String refreshUsingId(int id) {

        return refreshTokenRepository.findByUserId(id)
                .map(jwtService::verifyRefreshTokenExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    return jwtService.generateToken(user.getUsername());
                }).orElseThrow(() -> new RuntimeException(
                        "Refresh token is not in database!"));


    }
}
