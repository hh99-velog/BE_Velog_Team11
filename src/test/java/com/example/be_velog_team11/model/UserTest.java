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
                @DisplayName("????????????")
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
                @DisplayName("???????????? ???????????? ?????????")
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
                    assertEquals("??????????????? ???????????? ????????????", runtimeException.getMessage());
                }

                @Test
                @DisplayName("???????????? ???????????? ??????")
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
                    assertEquals("???????????? ????????? ????????????,?????? 4-12 ?????? ?????????", errorNotFoundUserException.getMessage());
                }

                @Test
                @DisplayName("???????????? ????????? ??????????????? ?????????")
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

                    assertEquals("????????? ????????? ????????????.", errorNotFoundUserException.getMessage());
                }

                @Test
                @DisplayName("????????? ????????? ?????????")
                void registerUserNicknameTypeNotEqual(){
                    String nickname = "?????????@@@@";
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
                    assertEquals("????????? ????????? ?????????????????????,?????? 3-10??? ??????", errorNotFoundUserException.getMessage());
                }
                
                @Test
                @DisplayName("????????????(email) ?????? ?????????")
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
                            .nickname("?????????")
                            .password(password)
                            .username(username)
                            .passwordConfirm(passwordConfirm)
                            .build();

                    ErrorNotFoundUserException errorNotFoundUserException = assertThrows(ErrorNotFoundUserException.class, () -> userService.registerUser(signupRequestDto1));
                    //then
                    
                    assertEquals("????????? ?????? ?????????", errorNotFoundUserException.getMessage());

                }

                @Test
                @DisplayName("????????? ??????")
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
                            .content(new ObjectMapper().writeValueAsString(signupRequestDto)))// ??????(Body) ????????? ???????????? ???????????? ?????? ObjectMapper??? ?????? ????????? JSON?????? ????????????.
                            .andExpect(status().isOk())
                            .andReturn().getResponse().getHeader("Authorization");

                    User user = userRepository.findById(aLong).orElseThrow(IllegalArgumentException::new);
                    assertEquals(user.getUsername(), username);
                    assertEquals(user.getNickname(), nickname);
                }

                @Test
                @DisplayName("???????????? ???????????? ?????? ????????? ??????")
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
                                .content(new ObjectMapper().writeValueAsString(signupRequestDto1)));// ??????(Body) ????????? ???????????? ???????????? ?????? ObjectMapper??? ?????? ????????? JSON?????? ????????????..andExpect(new ErrorNotFoundUserException(ErrorCode.ERROR_USER_ID)).;

                    });
                    assertEquals("?????? ????????? ???????????? ????????????.", errorNotFoundUserException.getMessage());
                }
            }
