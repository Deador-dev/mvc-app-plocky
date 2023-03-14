package com.deador.mvcapp.service;

import com.deador.mvcapp.entity.User;

import java.util.Optional;

public interface UserService {
    boolean createUser(User user);

    Optional<User> getUserByEmail(String email);
}
