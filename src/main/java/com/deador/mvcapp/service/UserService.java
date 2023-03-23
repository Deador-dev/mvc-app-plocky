package com.deador.mvcapp.service;

import com.deador.mvcapp.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface UserService {
    boolean createUser(User user);

    void sendMessageToEmail(User user);

    void createUserAndRequestLogin(User user, HttpServletRequest request);

    boolean activateUser(String activationCode);

    User getUserById(Long id);

    User getUserByEmail(String email);

    User getUserByActivationCode(String activationCode);
}
