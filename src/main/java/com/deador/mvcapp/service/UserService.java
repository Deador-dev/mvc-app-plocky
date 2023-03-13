package com.deador.mvcapp.service;

import com.deador.mvcapp.entity.User;

import java.util.Optional;

public interface UserService {
    void createUser(User user);

    Optional<User> findByEmail(String email);
}
