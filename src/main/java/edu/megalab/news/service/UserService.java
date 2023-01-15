package edu.megalab.news.service;

import edu.megalab.news.configuration.jwt.JwtUtils;
import edu.megalab.news.dto.JwtDto;
import edu.megalab.news.dto.UserDto;
import edu.megalab.news.dto.request.LoginRequest;
import edu.megalab.news.dto.request.RegisterRequest;
import edu.megalab.news.dto.request.UserEditRequest;
import edu.megalab.news.entity.User;
import edu.megalab.news.exception.FileException;
import edu.megalab.news.exception.FileWriteException;
import edu.megalab.news.exception.GCPFileUploadException;
import edu.megalab.news.exception.UsernameExistsException;
import edu.megalab.news.mapper.UserMapper;
import edu.megalab.news.repository.UserRepository;
import edu.megalab.news.utility.CloudStorageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final CloudStorageUtil cloudStorage;


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

    public UserDto edit(UserEditRequest editForm, User user) throws UsernameExistsException, FileException, FileWriteException, GCPFileUploadException {
        if (!isUsernameValid(editForm.getUsername(), user.getUsername()))
            throw new UsernameExistsException("Username " + editForm.getUsername() + " already exists!");
        String avatarUrl = uploadAvatar(editForm.getAvatar());
        User updated = userMapper.toEntity(editForm, avatarUrl, user);
        User saved = userRepository.save(updated);
        return userMapper.toDto(saved);
    }

    public boolean isUsernameValid(String newUsername, String oldUsername) {
        if (newUsername.equals(oldUsername))
            return true;
        return !userRepository.existsByUsername(newUsername);
    }

    private Authentication authenticate(LoginRequest loginRequest) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
    }

    private String uploadAvatar(MultipartFile file) throws FileException, FileWriteException, GCPFileUploadException {
        if (file == null)
            return "https://storage.googleapis.com/megalab-news/user.png";
        if (!cloudStorage.isImageFile(file))
            throw new FileException("Not image file has been uploaded!");
        return cloudStorage.uploadFile(file).getLink();
    }


}
