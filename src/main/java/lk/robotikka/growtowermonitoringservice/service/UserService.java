package lk.robotikka.growtowermonitoringservice.service;

import lk.robotikka.growtowermonitoringservice.domain.request.LoginRequest;
import lk.robotikka.growtowermonitoringservice.domain.request.UserRegisterRequest;
import lk.robotikka.growtowermonitoringservice.domain.response.LoginResponse;
import org.springframework.http.ResponseEntity;

import java.util.Locale;

public interface UserService {
    ResponseEntity<Object> createUser(UserRegisterRequest userRegisterRequest, Locale locale);

    ResponseEntity<Object> login(LoginRequest loginRequest, Locale locale);
}
