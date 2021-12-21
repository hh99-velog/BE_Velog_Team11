package com.example.be_velog_team11.model;

import com.example.be_velog_team11.dto.request.SignupRequestDto;
import com.example.be_velog_team11.exception.ErrorNotFoundUserException;
import com.example.be_velog_team11.exception.ErrorUtils.ErrorCode;
import com.example.be_velog_team11.repository.UserRepository;
import com.example.be_velog_team11.service.UserService;
import com.example.be_velog_team11.validator.UserValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.RollbackException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@EnableAutoConfiguration
@Transactional
@Rollback(value = true)
public class UserTest {

    private MockMvc mvc;
    @Autowired

    private WebApplicationContext context;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    BCryptPasswordEncoder encodePassword;
    @Autowired
    UserValidator userValidator;
   
 
        private Long id;
        private String username;
        private String nickname;
        private String password;
        private String passwordConfirm;

        @BeforeEach
        void setup(){
            username = "woojin126@naver.com";
            nickname = "woojin";
            password = "47429468bB";
            passwordConfirm = "47429468bB";

            mvc = MockMvcBuilders
                    .webAppContextSetup(this.context)
                    .apply(springSecurity())
                    .build();
        }

                @Test
                @DisplayName("회원가입")
                void registerUserSuccess(){
                    //given
                    SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                            .nickname(nickname)
                            .password(password)
                            .username(username)
                            .passwordConfirm(passwordConfirm)
                            .build();
                    Long userId = userService.registerUser(signupRequestDto);
                    //when
                    User user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
                    //then

                    assertEquals(user.getId(), userId);
                    assertEquals(user.getUsername(), username);
                    assertEquals(user.getNickname(), nickname);
                    assertTrue( encodePassword.matches(signupRequestDto.getPassword(),user.getPassword()));

                }


                @Test
                @DisplayName("회원가입 비밀번호 불일치")
                void registerUserPasswordNotEqual(){
                    String passwordConfirm = "1234";
                    //given
                    SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                            .nickname(nickname)
                            .password(password)
                            .username(username)
                            .passwordConfirm(passwordConfirm)
                            .build();

                    //when
                    RuntimeException runtimeException = assertThrows(ErrorNotFoundUserException.class, () -> UserValidator.validateUserRegister(signupRequestDto.getUsername(), signupRequestDto.getNickname(), signupRequestDto.getPassword(), signupRequestDto.getPasswordConfirm()));
                    //then
                    assertEquals("비밀번호가 일치하지 않습니다", runtimeException.getMessage());
                }

                @Test
                @DisplayName("회원가입 비밀번호 형식")
                void registerUserPasswordNotMatch(){
                    String password = "@@4";
                    //given
                    SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                            .nickname(nickname)
                            .password(password)
                            .username(username)
                            .passwordConfirm(password)
                            .build();

                    //when
                    ErrorNotFoundUserException errorNotFoundUserException = assertThrows(ErrorNotFoundUserException.class, () -> UserValidator.validateUserRegister(signupRequestDto.getUsername(), signupRequestDto.getNickname(), signupRequestDto.getPassword(), signupRequestDto.getPasswordConfirm()));
                    //then
                    assertEquals("비밀번호 형식은 영대소문,숫자 4-12 이내 입니다", errorNotFoundUserException.getMessage());
                }

                @Test
                @DisplayName("회원가입 아이디 이메일형식 불일치")
                void registerUserEmailNotEqual(){
                    String username = "woojin126naver.com";
                    //given
                    SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                            .nickname(nickname)
                            .password(password)
                            .username(username)
                            .passwordConfirm(passwordConfirm)
                            .build();
                    //when
                    ErrorNotFoundUserException errorNotFoundUserException = assertThrows(ErrorNotFoundUserException.class, () -> UserValidator.validateUserRegister(signupRequestDto.getUsername(), signupRequestDto.getNickname(), signupRequestDto.getPassword(), signupRequestDto.getPasswordConfirm()));
                    //then

                    assertEquals("이메일 형식이 아닙니다.", errorNotFoundUserException.getMessage());
                }

                @Test
                @DisplayName("닉네임 형식이 다를때")
                void registerUserNicknameTypeNotEqual(){
                    String nickname = "김우진@@@@";
                    //given
                    SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                            .nickname(nickname)
                            .password(password)
                            .username(username)
                            .passwordConfirm(passwordConfirm)
                            .build();

                    //when
                    ErrorNotFoundUserException errorNotFoundUserException = assertThrows(ErrorNotFoundUserException.class, () -> UserValidator.validateUserRegister(signupRequestDto.getUsername(), signupRequestDto.getNickname(), signupRequestDto.getPassword(), signupRequestDto.getPasswordConfirm()));
                    //then
                    assertEquals("닉네임 형식은 영대소문자한글,숫자 3-10자 이내", errorNotFoundUserException.getMessage());
                }
                
                @Test
                @DisplayName("유저네임(email) 중복 테스트")
                public void duplicateUsername(){
                    //given
                    SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                            .nickname(nickname)
                            .password(password)
                            .username(username)
                            .passwordConfirm(passwordConfirm)
                            .build();

                    userService.registerUser(signupRequestDto);

                    //when
                    SignupRequestDto signupRequestDto1 = SignupRequestDto.builder()
                            .nickname("그림죠")
                            .password(password)
                            .username(username)
                            .passwordConfirm(passwordConfirm)
                            .build();

                    ErrorNotFoundUserException errorNotFoundUserException = assertThrows(ErrorNotFoundUserException.class, () -> userService.registerUser(signupRequestDto1));
                    //then
                    
                    assertEquals("이메일 중복 입니다", errorNotFoundUserException.getMessage());

                }

                @Test
                @DisplayName("로그인 성공")
                void loginSuccess() throws Exception {
                    SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                            .nickname(nickname)
                            .password(password)
                            .username(username)
                            .passwordConfirm(passwordConfirm)
                            .build();

                    Long aLong = userService.registerUser(signupRequestDto);

                    String url = "http://localhost:" + 8080 + "/user/login";

                    mvc.perform(post(url)
                    .contentType(MediaType.ALL)
                            .content(new ObjectMapper().writeValueAsString(signupRequestDto)))// 본문(Body) 영역은 문자열로 표현하기 위해 ObjectMapper를 통해 문자열 JSON으로 변환한다.
                            .andExpect(status().isOk())
                            .andReturn().getResponse().getHeader("Authorization");

                    User user = userRepository.findById(aLong).orElseThrow(IllegalArgumentException::new);
                    assertEquals(user.getUsername(), username);
                    assertEquals(user.getNickname(), nickname);
                }

                @Test
                @DisplayName("회원가입 하지않은 유저 로그인 실패")
                void NotSignupUserLogin() throws Exception {
                    SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                            .nickname(nickname)
                            .password(password)
                            .username(username)
                            .passwordConfirm(passwordConfirm)
                            .build();

                    userService.registerUser(signupRequestDto);

                    SignupRequestDto signupRequestDto1 = SignupRequestDto.builder()
                            .nickname(nickname)
                            .password(password)
                            .username("woojin@naver.com")
                            .passwordConfirm(passwordConfirm)
                            .build();

                    String url = "http://localhost:" + 8080 + "/user/login";
                    
                    ErrorNotFoundUserException errorNotFoundUserException = assertThrows(ErrorNotFoundUserException.class, () -> {
                        mvc.perform(post(url)
                                .contentType(MediaType.ALL)
                                .content(new ObjectMapper().writeValueAsString(signupRequestDto1)));// 본문(Body) 영역은 문자열로 표현하기 위해 ObjectMapper를 통해 문자열 JSON으로 변환한다..andExpect(new ErrorNotFoundUserException(ErrorCode.ERROR_USER_ID)).;

                    });
                    assertEquals("유저 정보가 존재하지 않습니다.", errorNotFoundUserException.getMessage());
                }
            }
