package com.deador.mvcapp.service.impl;

import com.deador.mvcapp.entity.User;
import com.deador.mvcapp.exception.NotExistException;
import com.deador.mvcapp.exception.SendMessageToEmailException;
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
    private static final String USER_NOT_FOUND_EXCEPTION = "User not found";
    private static final String USER_NOT_FOUND_BY_EMAIL_EXCEPTION = "User not found by email: %s";
    private static final String USER_NOT_FOUND_BY_ID_EXCEPTION = "User not found by id: %s";
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
        if (user == null || user.getFirstName() == null || user.getLastName() == null || user.getEmail() == null || user.getPassword() == null) {
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

    public void sendMessageToEmail(User user) {
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            // FIXME: 20.03.2023 hardcode
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Plocky. Please, visit next link to activate account: http://localhost:8080/activate/%s",
                    user.getFirstName(),
                    user.getActivationCode()
            );
            if (!mailSenderService.send(user.getEmail(), "Activation code!", message)) {
                throw new SendMessageToEmailException();
            }
        } else {
            throw new NotExistException(USER_NOT_FOUND_EXCEPTION);
        }
    }

    // FIXME: 23.03.2023 request.login() doesn't work now because the newly created user has isActivated=false, so he can't login
    @Override
    public void createUserAndRequestLogin(User user, HttpServletRequest request) {
        String password = user.getPassword();
        if (createUser(user)) {
            try {
                if (!user.getIsActivated()) {
                    return;
                }
                // FIXME: 23.03.2023 password || user.getPassword()?
                request.login(user.getEmail(), password);
            } catch (ServletException e) {
                throw new UserAuthenticationException(USER_REQUEST_LOGIN_EXCEPTION);
            }
        }
    }

    @Override
    @Transactional
    public boolean activateUser(String activationCode) {
        User user = getUserByActivationCode(activationCode);

        if (user.getIsActivated()) {
            return false;
        }

        user.setActivationCode("Confirmed");
        user.setIsActivated(true);

        userRepository.save(user);

        return true;
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            throw new NotExistException(String.format(USER_NOT_FOUND_BY_ID_EXCEPTION, id));
        }

        return optionalUser.get();
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new NotExistException(String.format(USER_NOT_FOUND_BY_EMAIL_EXCEPTION, email));
        }

        return optionalUser.get();
    }

    @Override
    public User getUserByActivationCode(String activationCode) {
        Optional<User> optionalUser = userRepository.findByActivationCode(activationCode);

        if (optionalUser.isEmpty()) {
            throw new NotExistException(String.format(USER_NOT_FOUND_BY_ACTIVATION_CODE_EXCEPTION, activationCode));
        }

        return optionalUser.get();
    }
}
