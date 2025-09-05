package com.scaler.userservicesept25.services;

import com.scaler.userservicesept25.exceptions.InvalidTokenException;
import com.scaler.userservicesept25.exceptions.PasswordMismatchException;
import com.scaler.userservicesept25.models.Token;
import com.scaler.userservicesept25.models.User;

public interface UserService {
    User signUp(String name, String email, String password);

    Token login(String email, String password) throws PasswordMismatchException;

    User validateToken(String tokenValue) throws InvalidTokenException;
}
