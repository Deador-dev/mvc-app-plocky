package com.deador.mvcapp.repository;

import com.deador.mvcapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByActivationCode(String activationCode);

    boolean existsByEmail(String email);

}