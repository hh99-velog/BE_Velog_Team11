package com.example.be_velog_team11.service;

import com.example.be_velog_team11.dto.BoardRequestDto;
import com.example.be_velog_team11.dto.BoardResponseDto;
import com.example.be_velog_team11.model.Board;
import com.example.be_velog_team11.model.User;
import com.example.be_velog_team11.repository.BoardRepository;
import com.example.be_velog_team11.utils.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
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
    @Transactional
    public BoardResponseDto findBoard(Long board_id) {
        // board_id 조회
        Board findBoard = boardRepository.findById(board_id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다")
        );

        // Response Dto 반환
        return BoardResponseDto.builder()
                .id(findBoard.getId())
                .title(findBoard.getTitle())
                .content(findBoard.getContent())
                .img(findBoard.getImg())
                .nickname(findBoard.getUser().getNickname())
                .createdAt(findBoard.getCreatedAt())
                //like필요

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
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다")
        );

        // 사용자 조회 (작성자와 수정자가 같은지 확인)
        if (!findBoard.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("작성자와 수정자가 다릅니다");
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
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다")
        );

        // 사용자 조회 (작성자와 수정자가 같은지 확인)
        if (!findBoard.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("작성자와 삭제가 다릅니다");
        }

        // Board DB 삭제
        boardRepository.deleteById(board_id);
    }
}
