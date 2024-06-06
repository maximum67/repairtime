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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.sendRedirect("/auth/loginError?error="+getExceptionMessage(exception));
    }

    private static String getExceptionMessage(Exception exception) {

       return switch (exception.getClass().getName()) {
            case "org.springframework.security.authentication.BadCredentialsException" -> encodingURL("Неверный пароль");
            case "org.springframework.security.authentication.UsernameNotFoundException" -> encodingURL("Пользователь не найден");
            case "org.springframework.security.authentication.InternalAuthenticationServiceException" -> encodingURL("Неверное имя пользователя или пароль");
            case "org.springframework.security.core.userdetails.UsernameNotFoundException" -> encodingURL("Имя пользователя не найдено");
            case "org.springframework.security.web.authentication.session.SessionAuthenticationException" ->
                    encodingURL("Пользователь уже вошел в другом браузере");
            default -> "";
        };
    }

    private static String encodingURL(String inputString) {
        return  URLEncoder.encode(inputString, StandardCharsets.UTF_8);
    }
}
