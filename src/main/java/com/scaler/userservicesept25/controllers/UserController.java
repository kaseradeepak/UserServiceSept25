package com.scaler.userservicesept25.controllers;

import com.scaler.userservicesept25.dtos.LoginRequestDto;
import com.scaler.userservicesept25.dtos.SignUpRequestDto;
import com.scaler.userservicesept25.dtos.TokenDto;
import com.scaler.userservicesept25.dtos.UserDto;
import com.scaler.userservicesept25.models.User;
import com.scaler.userservicesept25.services.UserService;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public UserDto signUp(@RequestBody SignUpRequestDto requestDto) {
        User user = userService.signUp(
                requestDto.getName(),
                requestDto.getEmail(),
                requestDto.getPassword()
        );

        return UserDto.from(user);
    }

    @PostMapping("/login")
    public TokenDto login(@RequestBody LoginRequestDto requestDto) {
        return null;
    }

    @GetMapping("/validate/{tokenValue}")
    public UserDto validateToken(@PathVariable("tokenValue") String tokenValue) {
        return null;
    }

    //TODO
//    public logOut() {
//
//    }
}
