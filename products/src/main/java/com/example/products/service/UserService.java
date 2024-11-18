package com.example.products.service;

import com.example.products.dto.AuthenticationRequest;
import com.example.products.dto.AuthenticationResponse;
import com.example.products.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto getUser(int id);

    List<UserDto> getAllUsers();

    UserDto updateUser(int id,UserDto userDto);

    UserDto patchUser(int id,UserDto userDto);

    void deleteUser(int id);

    AuthenticationResponse verify(AuthenticationRequest request);

    String refresh(String refreshToken);

    String refreshUsingId(int id);

}
