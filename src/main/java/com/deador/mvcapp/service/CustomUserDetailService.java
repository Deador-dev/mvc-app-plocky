package com.deador.mvcapp.service;

import com.deador.mvcapp.entity.User;
import com.deador.mvcapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userFromDB = userRepository.getUserByEmail(email);
        if (userFromDB.isPresent()) {
            return userFromDB.map(CustomUserDetail::new).get();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
