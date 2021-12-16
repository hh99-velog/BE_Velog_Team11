package com.example.be_velog_team11.service;

import com.example.be_velog_team11.dto.request.BoardRequestDto;
import com.example.be_velog_team11.dto.response.BoardResponseDto;
import com.example.be_velog_team11.exception.ErrorNotFoundBoardException;
import com.example.be_velog_team11.exception.ErrorUtils.ErrorCode;
import com.example.be_velog_team11.model.Board;
import com.example.be_velog_team11.model.User;
import com.example.be_velog_team11.repository.BoardRepository;
import com.example.be_velog_team11.repository.LikeRepository;
import com.example.be_velog_team11.utils.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final LikeRepository likeRepository;
    private final S3Uploader s3Uploader;

    private final String imageDirName = "static";   // S3 폴더 경로

    // 게시글 작성
    @Transactional
    public void saveBoard(
            User user,
            BoardRequestDto boardRequestDto,
            MultipartFile multipartFile
    ) throws IOException {
        String imgUrl = "";

        // 이미지 첨부 있으면 URL 에 S3에 업로드된 파일 url 저장
        if (multipartFile.getSize() != 0) {
            imgUrl = s3Uploader.upload(multipartFile, imageDirName);
        }

        // Request Dto -> Board 객체 생성
        Board board = Board.builder()
                .user(user)
                .title(boardRequestDto.getTitle())
                .content(boardRequestDto.getContent())
                .img(imgUrl)                    // 이미지 있으면 url, 없으면 NULL
                .build();

        // Board DB 저장
        boardRepository.save(board);
    }

    // 게시글 상세 조회
    public BoardResponseDto findBoard(Long board_id) {
        // board_id 조회
        Board findBoard = boardRepository.findById(board_id).orElseThrow(
                () -> new ErrorNotFoundBoardException(ErrorCode.ERROR_BOARD_ID)
        );

        // Response Dto 반환
        return BoardResponseDto.builder()
                .id(findBoard.getId())
                .title(findBoard.getTitle())
                .content(findBoard.getContent())
                .img(findBoard.getImg())
                .nickname(findBoard.getUser().getNickname())
                .createdAt(String.valueOf(findBoard.getCreatedAt()))
                //like필요
                .like(likeRepository.countByBoard(findBoard).get())
                .build();
    }

    // 게시글 수정
    @Transactional
    public void modifyBoard(
            User user,
            Long board_id,
            BoardRequestDto boardRequestDto,
            MultipartFile multipartFile
    ) throws IOException {
        // board_id 조회
        Board findBoard = boardRepository.findById(board_id).orElseThrow(
                () -> new ErrorNotFoundBoardException(ErrorCode.ERROR_BOARD_ID)
        );

        // 사용자 조회 (작성자와 수정자가 같은지 확인)
        if (!findBoard.getUser().getId().equals(user.getId())) {
            throw new ErrorNotFoundBoardException(ErrorCode.ERROR_NOTMATCH_MODIFY);
        }

        String imgUrl = "";
        // 이미지 첨부 있으면 URL 에 S3에 업로드된 파일 url 저장 -> 객체 업데이트
        // 첨부 없으면 URL 에 NULL 값 저장 -> 객체 업데이트
        if (multipartFile.getSize() != 0) {
            imgUrl = s3Uploader.upload(multipartFile, imageDirName);
            findBoard.update(boardRequestDto, imgUrl);
        } else {
            findBoard.update(boardRequestDto, imgUrl);
        }

        // Board DB 수정
        boardRepository.save(findBoard);
    }

    // 게시글 삭제
    @Transactional
    public void deleteBoard(
            User user,
            Long board_id
    ) {
        // board_id 조회
        Board findBoard = boardRepository.findById(board_id).orElseThrow(
                () -> new ErrorNotFoundBoardException(ErrorCode.ERROR_BOARD_ID)
        );

        // 사용자 조회 (작성자와 수정자가 같은지 확인)
        if (!findBoard.getUser().getId().equals(user.getId())) {
            throw new ErrorNotFoundBoardException(ErrorCode.ERROR_NOTMATCH_DELETE);
        }

        // Board DB 삭제
        boardRepository.deleteById(board_id);
    }

    public List<BoardResponseDto> findAll() {
        List<Board> boardData = boardRepository.findAll();
        return boardData.stream().map(s -> new BoardResponseDto(s.getId(),s.getTitle(),s.getContent(),s.getUser().getNickname(),s.getImg(),String.valueOf(s.getCreatedAt()),likeRepository.countByBoard(s).orElse(0))).collect(Collectors.toList());
    }
}
