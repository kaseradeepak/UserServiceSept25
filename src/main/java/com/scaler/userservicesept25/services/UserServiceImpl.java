package com.scaler.userservicesept25.services;

import com.scaler.userservicesept25.exceptions.InvalidTokenException;
import com.scaler.userservicesept25.exceptions.PasswordMismatchException;
import com.scaler.userservicesept25.models.Token;
import com.scaler.userservicesept25.models.User;
import com.scaler.userservicesept25.repositories.TokenRepository;
import com.scaler.userservicesept25.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private TokenRepository tokenRepository;

    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public User signUp(String name, String email, String password) {
        //Check if there's already a user with the email or not.
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            //redirect to the login page.
            return optionalUser.get();
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        // User BCryptPasswordEncoder to encode the password.
        user.setPassword(bCryptPasswordEncoder.encode(password));

        return userRepository.save(user);
    }

    @Override
    public Token login(String email, String password) throws PasswordMismatchException {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            //redirect the user to the signUp page.
            return null;
        }

        User user = optionalUser.get();

        if(!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            //Login unsuccessful.
            throw new PasswordMismatchException("Incorrect password.");
        }

        //Login successful.
        //Generate the Token.
        Token token = new Token();
        token.setUser(user);

        /// random alphanumeric string of 128 characters.
        token.setTokenValue(RandomStringUtils.randomAlphanumeric(128));

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 30);
        Date expiryDate = calendar.getTime();
        token.setExpiryAt(expiryDate);

        return tokenRepository.save(token);
    }

    @Override
    public User validateToken(String tokenValue) throws InvalidTokenException {
        Optional<Token> optionalToken = tokenRepository.findByTokenValueAndExpiryAtGreaterThan(
                tokenValue,
                new Date());

        if (optionalToken.isEmpty()) {
            //Invalid token
            throw new InvalidTokenException("Invalid token.");
        }

        //Token is valid.
        Token token = optionalToken.get();
        return token.getUser();
    }
}
