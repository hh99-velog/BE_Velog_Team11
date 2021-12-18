package com.example.be_velog_team11.service;

import com.example.be_velog_team11.dto.request.SignupRequestDto;
import com.example.be_velog_team11.exception.ErrorNotFoundBoardException;
import com.example.be_velog_team11.exception.ErrorNotFoundUserException;
import com.example.be_velog_team11.exception.ErrorUtils.ErrorCode;
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
    public Long registerUser(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String nickname = requestDto.getNickname();
        String password = requestDto.getPassword();
        String passwordConfirm = requestDto.getPasswordConfirm();

        // 회원 ID 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new ErrorNotFoundUserException(ErrorCode.ERROR_DUPLICATE_EMAIL);
        }

        // 닉네임 중복 확인
        Optional<User> found_nickname = userRepository.findByNickname(nickname);
        if (found_nickname.isPresent()) {
            throw new ErrorNotFoundUserException(ErrorCode.ERROR_DUPLICATE_NICKNAME);
        }

        // 회원가입 유효성 검사
        UserValidator.validateUserRegister(username, nickname, password, passwordConfirm);

        // 패스워드 암호화
        String encodePassword = passwordEncoder.encode(password);

        User user = new User(username, nickname, encodePassword);
        userRepository.save(user);

        return user.getId();
    }

    // username 중복체크
    public boolean usernameCheck(String username) {
        return userRepository.existsByUsername(username);
    }

    // nickname 중복체크
    public boolean nicknameCheck(String nickname) {
        return userRepository.existsByNickname(nickname);
    }
}