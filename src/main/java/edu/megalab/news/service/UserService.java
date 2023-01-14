package edu.megalab.news.service;

import edu.megalab.news.configuration.jwt.JwtUtils;
import edu.megalab.news.dto.JwtDto;
import edu.megalab.news.dto.request.LoginRequest;
import edu.megalab.news.dto.request.RegisterRequest;
import edu.megalab.news.dto.UserDto;
import edu.megalab.news.entity.User;
import edu.megalab.news.exception.UsernameExistsException;
import edu.megalab.news.mapper.UserMapper;
import edu.megalab.news.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;


    public UserDto register(RegisterRequest registerForm) throws UsernameExistsException {
        if (userRepository.existsByUsername(registerForm.getUsername()))
            throw new UsernameExistsException("Username " + registerForm.getUsername() + " already exists!");
        registerForm.setPassword(encoder.encode(registerForm.getPassword()));
        User user = userMapper.toEntity(registerForm);
        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    public JwtDto login(LoginRequest loginRequest) {
        Authentication auth = authenticate(loginRequest);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String token = jwtUtils.generateJwtToken(auth);
        User user = (User) auth.getPrincipal();
        return userMapper.toJwtDto(user, token, "Bearer");
    }

    private Authentication authenticate(LoginRequest loginRequest) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
    }
}
