package com.deador.mvcapp.service.userdetail;

import com.deador.mvcapp.entity.User;
import com.deador.mvcapp.exception.UserAuthenticationException;
import com.deador.mvcapp.exception.UserNotActivatedException;
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
    public UserDetails loadUserByUsername(String email) {
        Optional<User> userFromDB = userRepository.findByEmail(email);

        userFromDB.ifPresent(user -> {
            if (user.getIsActivated() == null || !user.getIsActivated()) {
                throw new UserNotActivatedException();
            }
        });

        return userFromDB.map(CustomUserDetail::new)
                .orElseThrow(() -> new UserAuthenticationException("User not found"));
    }
}
