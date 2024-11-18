package com.example.products.security;

import com.example.products.entity.User;
import com.example.products.exception.ResourceNotFoundException;
import com.example.products.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username)
                .orElseThrow(
                        ()->new ResourceNotFoundException("User not found"));


        return new CustomUserDetail(user.getUsername(),user.getPassword());
    }
}