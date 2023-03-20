package com.deador.mvcapp.service.impl;

import com.deador.mvcapp.entity.User;
import com.deador.mvcapp.exception.NotExistException;
import com.deador.mvcapp.repository.UserRepository;
import com.deador.mvcapp.service.CartService;
import com.deador.mvcapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static final String USER_NOT_FOUND_BY_EMAIL = "User not found by email: %s";
    private final CartService cartService;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(CartService cartService, UserRepository userRepository) {
        this.cartService = cartService;
        this.userRepository = userRepository;
    }

    // FIXME: 17.03.2023 need to use custom exceptions
    @Override
    public boolean createUser(User user) {
        userRepository.save(user);
        cartService.createCartForUser(user);
        return true;
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new NotExistException(String.format(USER_NOT_FOUND_BY_EMAIL, email));
        }

        return optionalUser;
    }
}
