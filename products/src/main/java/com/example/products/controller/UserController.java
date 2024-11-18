package com.example.products.controller;

import com.example.products.dto.AuthenticationRequest;
import com.example.products.dto.AuthenticationResponse;
import com.example.products.dto.UserDto;
import com.example.products.mapper.UserMapper;
import com.example.products.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        if (userDto.getUsername() == null || userDto.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username must not be null or empty");
        }
        UserDto savedUser = userService.createUser(userDto);
        return  new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") int id){
        UserDto userDto = userService.getUser(id);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/getAll")
    public  ResponseEntity<List<UserDto>> getALlUsers(){
        List<UserDto> userList = userService.getAllUsers();
        return ResponseEntity.ok(userList);
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") int id, @Valid @RequestBody UserDto updated){
        UserDto userDto = userService.updateUser(id,updated);
        return new ResponseEntity<>(userDto,HttpStatus.CREATED);
    }

    @PatchMapping("{id}")
    public ResponseEntity<UserDto> patchUser(@PathVariable("id") int id, @Valid @RequestBody UserDto patch){
        UserDto userDto = userService.patchUser(id,patch);
        return new ResponseEntity<>(userDto,HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") int id){
        userService.deleteUser(id);
        return new ResponseEntity<>("user deleted",HttpStatus.NO_CONTENT);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
        AuthenticationResponse token = userService.verify(request);
        return ResponseEntity.ok(token);
    }


    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(@RequestHeader("Authorization") String refreshToken) {
        String accessToken = userService.refresh(refreshToken);
        return  ResponseEntity.ok(accessToken);
    }

    @PostMapping("/refreshUsingId")
    public ResponseEntity<String> refreshUsingId(@RequestHeader("Id") int id) {

        String accessToken = userService.refreshUsingId(id);
        return  ResponseEntity.ok(accessToken);
    }

}
