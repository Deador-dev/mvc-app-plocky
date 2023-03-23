package com.deador.mvcapp;

import com.deador.mvcapp.entity.Role;
import com.deador.mvcapp.entity.User;

import java.util.Collections;

import static com.deador.mvcapp.TestConstants.*;

public class TestUtils {
    public static User getActivatedUser() {
        return User.builder()
                .id(USER_ID_ACTIVATED)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL_ACTIVATED)
                .password(PASSWORD)
                .activationCode(ACTIVATED_ACTIVATION_CODE)
                .isActivated(IS_ACTIVATED)
                .roles(Collections.singletonList(getUserRole()))
                .build();
    }

    public static User getNotActivatedUser() {
        return User.builder()
                .id(USER_ID_NOT_ACTIVATED)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL_NOT_ACTIVATED)
                .password(PASSWORD)
                .activationCode(NOT_ACTIVATED_ACTIVATION_CODE)
                .isActivated(IS_NOT_ACTIVATED)
                .roles(Collections.singletonList(getUserRole()))
                .build();
    }

    public static Role getUserRole() {
        return Role.builder()
                .id(ROLE_USER_ID)
                .name(ROLE_USER_NAME)
                .build();
    }
}
