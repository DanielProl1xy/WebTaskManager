package com.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.Token;
import com.entity.User;
import com.entity.User.Role;
import com.repository.TokenRepository;
import com.repository.UserRepository;

@Service
public class LoginService {

    public class LoginForm {

        private final String email;
        private final Integer passwordHash;

        public LoginForm(String email, int passwordHash) {
            this.email = email;
            this.passwordHash = passwordHash;
        }

        public String getEmail() {
            return email;
        }

        public int getPasswordHash() {
            return passwordHash;
        }
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    public User validateToken(String token, boolean checkIfAdmin) {
        Token tkn = tokenRepository.findByToken(token)
        .orElseThrow( 
            () -> new IllegalArgumentException("Invalid token provided")
        );

        // NoSuchElementException
        User user = userRepository.findById(tkn.getUserid()).get(); 

        if(user.getRole() != Role.ADMIN && checkIfAdmin) 
            throw new IllegalCallerException("User " + user.getEmail() + " has no rights to perform action");

        return user;
    }

    public Token registerWithForm(LoginForm form) {

        userRepository.findByEmail(form.getEmail()).ifPresent(
            (User user) -> { throw new IllegalAccessError("User with email " + form.getEmail() + " is already registered"); }
        );

        User user = new User(form.getEmail(), Role.USER, form.getPasswordHash());

        userRepository.save(user);

        return loginWithForm(form);
    }

    public Token loginWithForm(LoginForm form) {

        User user = userRepository.findByEmail(form.getEmail())
        .orElseThrow(
            () -> new IllegalArgumentException("User with email " + form.getEmail() + " was not found")
        );

        if(user.getPasswordHash() != form.getPasswordHash())
        {
            throw new IllegalArgumentException("Invalid password for user " + user.getEmail());
        }

        Token token = new Token(user.getId(), UUID.randomUUID().toString());

        tokenRepository.save(token);

        return token;
    }
}
