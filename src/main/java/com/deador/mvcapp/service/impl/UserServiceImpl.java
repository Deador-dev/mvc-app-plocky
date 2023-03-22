package com.deador.mvcapp.service.impl;

import com.deador.mvcapp.entity.User;
import com.deador.mvcapp.exception.NotExistException;
import com.deador.mvcapp.exception.UserAuthenticationException;
import com.deador.mvcapp.repository.RoleRepository;
import com.deador.mvcapp.repository.UserRepository;
import com.deador.mvcapp.service.CartService;
import com.deador.mvcapp.service.MailSenderService;
import com.deador.mvcapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private static final String INVALID_DATA_INPUT_EXCEPTION = "Invalid data input";
    private static final String USER_NOT_FOUND_BY_EMAIL_EXCEPTION = "User not found by email: %s";
    private static final String USER_NOT_FOUND_BY_ACTIVATION_CODE_EXCEPTION = "User not found by activation code: %s";
    private static final String USER_WITH_THIS_EMAIL_ALREADY_EXISTS_EXCEPTION = "User with this email already exists: %s";
    private static final String USER_REQUEST_LOGIN_EXCEPTION = "Request login error";
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CartService cartService;
    private final MailSenderService mailSenderService;

    @Autowired
    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           CartService cartService,
                           MailSenderService mailSenderService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.cartService = cartService;
        this.mailSenderService = mailSenderService;
    }

    @Override
    @Transactional
    public boolean createUser(User user) {
        if (user == null || user.getEmail() == null || user.getPassword() == null) {
            throw new UserAuthenticationException(INVALID_DATA_INPUT_EXCEPTION);
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAuthenticationException(String.format(USER_WITH_THIS_EMAIL_ALREADY_EXISTS_EXCEPTION, user.getEmail()));
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singletonList(roleRepository.findByName("ROLE_USER").get()));
        user.setIsActivated(false);
        user.setActivationCode(UUID.randomUUID().toString());


        userRepository.save(user);
        cartService.createCartForUser(user);

        sendMessageToEmail(user);

        return true;
    }

    private void sendMessageToEmail(User user) {
        if (!user.getEmail().isEmpty()) {
            // FIXME: 20.03.2023 hardcode
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Plocky. Please, visit next link to activate account: http://localhost:8080/activate/%s",
                    user.getFirstName(),
                    user.getActivationCode()
            );
            mailSenderService.send(user.getEmail(), "Activation code!", message);
        }
    }

    @Override
    public void createUserAndRequestLogin(User user, HttpServletRequest request) {
        String password = user.getPassword();
        if (createUser(user)) {
            try {
                // FIXME: 21.03.2023 why don't work request.login() ?
                request.login(user.getEmail(), password);
            } catch (ServletException e) {
                throw new UserAuthenticationException(USER_REQUEST_LOGIN_EXCEPTION);
            }
        }
    }

    @Override
    @Transactional
    public boolean activateUser(String activationCode) {
        Optional<User> optionalUser = userRepository.findByActivationCode(activationCode);
        if (optionalUser.isEmpty()) {
            throw new NotExistException(String.format(USER_NOT_FOUND_BY_ACTIVATION_CODE_EXCEPTION, activationCode));
        }

        User user = optionalUser.get();
        user.setActivationCode("Confirmed");
        user.setIsActivated(true);

        userRepository.save(user);

        return true;
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new NotExistException(String.format(USER_NOT_FOUND_BY_EMAIL_EXCEPTION, email));
        }

        return optionalUser.get();
    }
}
