package com.example.be_velog_team11.model;

import com.example.be_velog_team11.dto.request.BoardRequestDto;
import com.example.be_velog_team11.exception.ErrorNotFoundBoardException;
import com.example.be_velog_team11.repository.BoardRepository;
import com.example.be_velog_team11.repository.UserRepository;
import com.example.be_velog_team11.service.BoardService;
import com.example.be_velog_team11.service.LikeService;
import com.example.be_velog_team11.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class BoardTest {


    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;


    private String title;
    private String content;


    @BeforeEach
    void setup() {
        title = "안녕";
        content = "안녕하세요";
    }

    @Nested
    @DisplayName("게시글 등록")
    class boardEnroll {
        User user;
        MockMultipartFile mockMultipartFile;

        @BeforeEach
        void registerUser() {
            mockMultipartFile = new MockMultipartFile(
                    "image1", "image1", "application/doc", "image".getBytes());

            user = User.builder()
                    .id(1L)
                    .username("woojin126@naver.com")
                    .password("47429468bB")
                    .nickname("woojin")
                    .build();

            userRepository.save(user);

        }

        @Nested
        @DisplayName("게시글 등록 성공 케이스")
        class SuccessBoardEnroll {
            @Test
            @DisplayName("정상 케이스")
            void 게시글등록성공() throws IOException {

                BoardRequestDto boardRequestDto = BoardRequestDto.builder()
                        .content(content)
                        .title(title)
                        .build();

                MockMultipartFile mockMultipartFile = new MockMultipartFile(
                        "image1", "image1", "application/doc", "image".getBytes());
                Long boardId = boardService.saveBoard(user, boardRequestDto, mockMultipartFile);


                Board board = boardRepository.findById(boardId).get();

                assertEquals(content, board.getContent());
                assertEquals(title, board.getTitle());
            }
        }
        
        @Nested
        @DisplayName("게시글 등록 실패 케이스")
        class FailBoardEnroll{

            @Test
            @DisplayName("실패 케이스1 / 제목입력")
            void 제목입력안했을때() throws IOException {
                //given
                BoardRequestDto boardRequestDto = BoardRequestDto.builder()
                        .content(content)
                        .title(null)
                        .build();


                //when
                NullPointerException nullPointerException = assertThrows(NullPointerException.class, () -> {
                    boardService.saveBoard(user, boardRequestDto, mockMultipartFile);
                });
            }
        }
    }
}