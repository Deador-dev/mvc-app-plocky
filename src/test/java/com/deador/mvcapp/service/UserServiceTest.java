package com.deador.mvcapp.service;

import com.deador.mvcapp.TestUtils;
import com.deador.mvcapp.entity.Role;
import com.deador.mvcapp.entity.User;
import com.deador.mvcapp.exception.AlreadyExistException;
import com.deador.mvcapp.exception.NotExistException;
import com.deador.mvcapp.exception.SendMessageToEmailException;
import com.deador.mvcapp.exception.UserAuthenticationException;
import com.deador.mvcapp.repository.RoleRepository;
import com.deador.mvcapp.repository.UserRepository;
import com.deador.mvcapp.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private static final Long EXISTING_ID = 1L;
    private static final Long NOT_EXISTING_ID = 99L;
    private static final String FIRST_NAME = "admin";
    private static final String LAST_NAME = "admin";
    private static final String EXISTING_EMAIL = "admin@gmail.com";
    private static final String NOT_EXISTING_EMAIL = "not_existing@gmail.com";
    private static final String PASSWORD = "123";
    private static final String ENCODED_PASSWORD = "$2a$10$LMGEnMQ8b/ndBvf.qks7aOWJsmXCnV04BlcqfK9NI0Cd/XmLhhn5C";
    private static final String EXISTING_NOT_ACTIVATED_ACTIVATION_CODE = "ad956602-bce5-4234-a881-a7164e69a299";
    private static final String NOT_EXISTING_ACTIVATION_CODE = "Wrong_activation_code";
    private static final String ACTIVATED_ACTIVATION_CODE = "Confirmed";
    private static final Boolean IS_ACTIVATED = true;
    private static final Long ROLE_ID = 1L;
    private static final String ROLE_NAME = "ROLE_USER";
    private static final String USER_NOT_FOUND_EXCEPTION = "User not found";
    private static final String USER_NOT_FOUND_BY_ID_EXCEPTION = "User not found by id: %s";
    private static final String USER_NOT_FOUND_BY_EMAIL_EXCEPTION = "User not found by email: %s";
    private static final String USER_NOT_FOUND_BY_ACTIVATION_CODE_EXCEPTION = "User not found by activation code: %s";
    private static final String USER_WITH_THIS_EMAIL_ALREADY_EXISTS_EXCEPTION = "User with this email already exists: %s";
    private static final String INVALID_DATA_INPUT_EXCEPTION = "Invalid data input";
    private static final String SEND_MESSAGE_TO_EMAIL_EXCEPTION = "Send message to email exception";


    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private CartService cartService;
    @Mock
    private MailSenderService mailSenderService;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserServiceImpl userService;
    private User user;
    private Role role;

    @BeforeEach
    void init() {
        role = Role.builder().id(ROLE_ID).name(ROLE_NAME).build();
        user = User.builder().id(EXISTING_ID).firstName(FIRST_NAME).lastName(LAST_NAME).email(EXISTING_EMAIL)
                .password(PASSWORD).activationCode(ACTIVATED_ACTIVATION_CODE).isActivated(IS_ACTIVATED).roles(Collections.singletonList(role))
                .build();
    }

    @Test
    void getUserByExistingIdTest() {
        when(userRepository.findById(EXISTING_ID)).thenReturn(Optional.of(user));

        User actualUser = userService.getUserById(EXISTING_ID);

        assertEquals(actualUser, user);
    }

    @Test
    void getUserByNotExistingIdTest() {
        when(userRepository.findById(NOT_EXISTING_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById(NOT_EXISTING_ID)).isInstanceOf(NotExistException.class);
    }

    @Test
    void givenId_whenGetUserById_thenReturnUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        User actualUser = userService.getUserById(anyLong());

        assertThat(actualUser).isNotNull().isEqualTo(user);
    }

    @Test
    void givenId_whenGetUserById_thenThrowNotExistException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById(EXISTING_ID)).isInstanceOf(NotExistException.class)
                .hasMessage(String.format(USER_NOT_FOUND_BY_ID_EXCEPTION, EXISTING_ID));
    }

    @Test
    void getUserByExistingEmailTest() {
        when(userRepository.findByEmail(EXISTING_EMAIL)).thenReturn(Optional.of(user));

        User actualUser = userService.getUserByEmail(EXISTING_EMAIL);

        assertEquals(actualUser, user);
    }

    @Test
    void getUserByNotExistingEmailTest() {
        when(userRepository.findByEmail(NOT_EXISTING_EMAIL)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserByEmail(NOT_EXISTING_EMAIL)).isInstanceOf(NotExistException.class);
    }

    @Test
    void givenEmail_whenGetUserByEmail_thenReturnUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        User actualUser = userService.getUserByEmail(anyString());

        assertThat(actualUser).isNotNull().isEqualTo(user);
    }

    @Test
    void givenEmail_whenGetUserByEmail_thenThrowNotExistException() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserByEmail(EXISTING_EMAIL)).isInstanceOf(NotExistException.class)
                .hasMessage(String.format(USER_NOT_FOUND_BY_EMAIL_EXCEPTION, EXISTING_EMAIL));
    }

    @Test
    void getUserByExistingActivationCodeTest() {
        when(userRepository.findByActivationCode(EXISTING_NOT_ACTIVATED_ACTIVATION_CODE)).thenReturn(Optional.of(user));

        User actualUser = userService.getUserByActivationCode(EXISTING_NOT_ACTIVATED_ACTIVATION_CODE);

        assertEquals(actualUser, user);
    }

    @Test
    void getUserByNotExistingActivationCode() {
        when(userRepository.findByActivationCode(NOT_EXISTING_ACTIVATION_CODE)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserByActivationCode(NOT_EXISTING_ACTIVATION_CODE)).isInstanceOf(NotExistException.class);
    }

    @Test
    void givenActivationCode_whenGetUserByActivationCode_thenReturnUser() {
        when(userRepository.findByActivationCode(anyString())).thenReturn(Optional.of(user));

        User actualUser = userService.getUserByActivationCode(anyString());
        assertThat(actualUser).isNotNull().isEqualTo(user);
    }

    @Test
    void givenActivationCode_whenGetUserByActivationCode_thenThrowNotExistException() {
        when(userRepository.findByActivationCode(anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserByActivationCode(EXISTING_NOT_ACTIVATED_ACTIVATION_CODE)).isInstanceOf(NotExistException.class)
                .hasMessage(String.format(USER_NOT_FOUND_BY_ACTIVATION_CODE_EXCEPTION, EXISTING_NOT_ACTIVATED_ACTIVATION_CODE));
    }

    @Test
    void givenUser_whenCreateUser_thenReturnTrue() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(roleRepository.findByName(ROLE_NAME)).thenReturn(Optional.of(role));
        when(mailSenderService.send(anyString(), anyString(), anyString())).thenReturn(true);

        assertTrue(userService.createUser(User.builder().firstName("Jon").lastName("Snow").email("newJon@gmail.com").password("123").build()));
    }

    @Test
    void givenNullUser_whenCreateUser_thenThrowUserAuthenticationException() {
        assertThatThrownBy(() -> userService.createUser(null))
                .isInstanceOf(UserAuthenticationException.class)
                .hasMessage(INVALID_DATA_INPUT_EXCEPTION);
    }

    @Test
    void givenUserWithNullFirstName_whenCreateUser_thenThrowUserAuthenticationException() {
        assertThatThrownBy(() -> userService.createUser(User.builder().firstName(null).build()))
                .isInstanceOf(UserAuthenticationException.class)
                .hasMessage(INVALID_DATA_INPUT_EXCEPTION);
    }
    // TODO: 23.03.2023 need to create tests for User another null fields

    @Test
    void givenEmail_whenCreateUserWithAnAlreadyExistingEmail_thenThrowUserAuthenticationException() {
        when(userRepository.findByEmail(EXISTING_EMAIL)).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> userService.createUser(user))
                .isInstanceOf(UserAuthenticationException.class)
                .hasMessage(String.format(USER_WITH_THIS_EMAIL_ALREADY_EXISTS_EXCEPTION, EXISTING_EMAIL));
    }

    @Test
    void givenUser_whenSendMessageToEmail_thenEmailSent() {
        when(mailSenderService.send(anyString(), anyString(), anyString())).thenReturn(true);
        user.setActivationCode(EXISTING_NOT_ACTIVATED_ACTIVATION_CODE);

        String message = String.format(
                "Hello, %s! \n" +
                        "Welcome to Plocky. Please, visit next link to activate account: http://localhost:8080/activate/%s",
                user.getFirstName(),
                user.getActivationCode()
        );

        userService.sendMessageToEmail(user);

        verify(mailSenderService, times(1)).send(eq(user.getEmail()), eq("Activation code!"), eq(message));
    }

    @Test
    void givenUserWithoutEmail_whenSendMessageToEmail_thenEmailNotSent() {
        assertThatThrownBy(() -> userService.sendMessageToEmail(User.builder().email(null).build()))
                .isInstanceOf(NotExistException.class)
                .hasMessage(USER_NOT_FOUND_EXCEPTION);

        assertThatThrownBy(() -> userService.sendMessageToEmail(User.builder().email("").build()))
                .isInstanceOf(NotExistException.class)
                .hasMessage(USER_NOT_FOUND_EXCEPTION);

        verify(mailSenderService, times(0)).send(anyString(), anyString(), anyString());
    }

    @Test
    void givenUser_whenSendMessageToEmail_thenThrowNotExistException() {
        when(mailSenderService.send(anyString(), anyString(), anyString())).thenReturn(false);

        assertThatThrownBy(() -> userService.sendMessageToEmail(user))
                .isInstanceOf(SendMessageToEmailException.class)
                .hasMessage(SEND_MESSAGE_TO_EMAIL_EXCEPTION);
    }

    // TODO: 23.03.2023 need to create tests for createUserAndRequestLogin()
    //  BUT it is doesn't work now because the newly created user has isActivated=false, so he can't login

    // TODO: 23.03.2023 need to create tests for activateUser()
    @Test
    void givenActivationCode_whenActivateUser_thenReturnTrue() {

    }
}