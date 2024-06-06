package com.example.repairtime.configuration;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;

public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.sendRedirect("/auth/loginError?error="+getExceptionMessage(exception));
    }

    private static String getExceptionMessage(Exception exception){

        return switch (exception.getClass().getName()) {
            case "org.springframework.security.authentication.BadCredentialsException" -> "Incorrect password";
            case "org.springframework.security.authentication.UsernameNotFoundException" -> "User is not registered";
            case "org.springframework.security.authentication.InternalAuthenticationServiceException" ->
                    "Incorrect username or password";
            case "org.springframework.security.core.userdetails.UsernameNotFoundException" -> "Username not found";
            case "org.springframework.security.web.authentication.session.SessionAuthenticationException" ->
                    "User is already logged in";
            default -> "";
        };
    }
}
