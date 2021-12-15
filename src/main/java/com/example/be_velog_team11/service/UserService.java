package com.example.be_velog_team11.service;

import com.example.be_velog_team11.dto.SignupRequestDto;
import com.example.be_velog_team11.model.User;
import com.example.be_velog_team11.repository.UserRepository;
import com.example.be_velog_team11.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String nickname = requestDto.getNickname();
        String password = requestDto.getPassword();
        String passwordConfirm = requestDto.getPasswordConfirm();

        // 회원 ID 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
        }

        // 닉네임 중복 확인
        Optional<User> found_nickname = userRepository.findByNickname(nickname);
        if (found_nickname.isPresent()) {
            throw new IllegalArgumentException("중복된 닉네임이 존재합니다.");
        }

        // 회원가입 유효성 검사
        UserValidator.validateUserRegister(username, nickname, password, passwordConfirm);

        // 패스워드 암호화
        String encodePassword = passwordEncoder.encode(password);

        User user = new User(username, nickname, encodePassword);
        userRepository.save(user);
    }
}