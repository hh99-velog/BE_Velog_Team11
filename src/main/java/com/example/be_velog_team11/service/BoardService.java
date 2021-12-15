package com.example.be_velog_team11.service;

import com.example.be_velog_team11.dto.BoardRequestDto;
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
}
