package com.deador.mvcapp.service;

import com.deador.mvcapp.entity.User;
import com.deador.mvcapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final CartService cartService;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(CartService cartService, UserRepository userRepository) {
        this.cartService = cartService;
        this.userRepository = userRepository;
    }

    @Override
    public boolean createUser(User user) {
        userRepository.save(user);
        cartService.createCartForUser(user);
        return true;
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }
}
