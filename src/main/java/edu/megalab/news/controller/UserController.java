package edu.megalab.news.controller;

import edu.megalab.news.dto.request.LoginRequest;
import edu.megalab.news.dto.request.RegisterRequest;
import edu.megalab.news.dto.UserDto;
import edu.megalab.news.dto.request.UserEditRequest;
import edu.megalab.news.entity.User;
import edu.megalab.news.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(userService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.login(loginRequest));
    }

    @PostMapping("/edit")
    public ResponseEntity<UserDto> editUser(@Valid @ModelAttribute UserEditRequest editForm,
                                            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userService.edit(editForm, user));
    }
}

