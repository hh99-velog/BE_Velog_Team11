package com.example.be_velog_team11.service;

import com.example.be_velog_team11.dto.response.CommentResponseDto;
import com.example.be_velog_team11.exception.ErrorNotFoundBoardException;
import com.example.be_velog_team11.exception.ErrorUtils.ErrorCode;
import com.example.be_velog_team11.model.Board;
import com.example.be_velog_team11.model.Comment;
import com.example.be_velog_team11.repository.BoardRepository;
import com.example.be_velog_team11.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public List<CommentResponseDto> getAllComments(Long board_id) {
        Board findBoard = boardRepository.findById(board_id).orElseThrow(
                () -> new ErrorNotFoundBoardException(ErrorCode.ERROR_BOARD_ID)
        );

        List<Comment> commentList = commentRepository.findAllByBoardOrderByCreatedAtDesc(findBoard);
        List<CommentResponseDto> comments = new ArrayList<>();
        for (Comment com : commentList) {
            CommentResponseDto commentResponseDto = CommentResponseDto.builder()
                                                        .nickname(com.getUser().getNickname())
                                                        .content(com.getContent())
                                                        .createdAt(com.getCreatedAt())
                                                        .build();

            comments.add(commentResponseDto);
        }

        return comments;
    }
}
