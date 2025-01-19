package com;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.entity.Token;
import com.entity.User;
import com.entity.User.Role;
import com.exception.AlreadyRegisteredException;
import com.service.LoginService;
import com.service.LoginService.LoginForm;


@SpringBootTest
public class LoginServiceTest {
    
    @Autowired
    private LoginService loginService;
    
    // For the test case
    private final String email = "test@test.net"; 
    private final String password = "123xxx123";

    private Token registeredToken;

    @BeforeAll
    public void setUp() {
        LoginForm form = new LoginForm(email, password.hashCode());

        registeredToken = loginService.registerWithForm(form);

        assertNotNull(registeredToken);
    }

    @Test
    public void testRegister() {

        assertNotNull(registeredToken);

        final User user = loginService.getUserById(registeredToken.getUserid());

        assertNotNull(user);
        assertEquals(email, user.getEmail());
        assertEquals(user.getPasswordHash(), password.hashCode());
        assertEquals(Role.USER, user.getRole());
    }

    @Test
    public void testLogin() {

        LoginForm form = new LoginForm(email, password.hashCode());
        final Token token = loginService.loginWithForm(form);

        assertNotNull(token);

        final User user = loginService.getUserById(token.getUserid());

        assertNotNull(user);
        assertEquals(email, user.getEmail());
        assertEquals(user.getPasswordHash(), password.hashCode());
        assertEquals(Role.USER, user.getRole());
    }

    @Test
    public void shouldThrowIllegalArgumentOnRegister() {

        LoginForm form = new LoginForm("abc", 0);

        assertThrows(IllegalArgumentException.class, () -> {
            loginService.registerWithForm(form);
        });
    }

    @Test
    public void shouldThrowIllegalArgumentOnLogin() {

        LoginForm form = new LoginForm("abc", 0);

        assertThrows(IllegalArgumentException.class, () -> {
            loginService.loginWithForm(form);
        });
    }

    @Test
    public void shouldThrowIfNotRegistered() {

        LoginForm form = new LoginForm("test2@list.net", password.hashCode());

        assertThrows(IllegalArgumentException.class, () -> {
            loginService.loginWithForm(form);
        });
    }

    @Test
    public void shouldTrowAlreadyRegistered() {
        assertThrows(AlreadyRegisteredException.class, () -> {
            LoginForm form = new LoginForm(email, password.hashCode());
            loginService.registerWithForm(form);
        });
    }

    @Test
    public void validTokenThenSuccess() {
        loginService.validateTokenElseThrow(registeredToken.getToken(), false);
    }

    @Test
    public void shouldThrowIfNotAdmin() {
        assertThrows(IllegalCallerException.class, () -> {
            loginService.validateTokenElseThrow(registeredToken.getToken(), true);
        });        
    }
}
