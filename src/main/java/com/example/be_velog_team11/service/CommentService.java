package com.example.be_velog_team11.service;

import com.example.be_velog_team11.dto.response.CommentResponseDto;
import com.example.be_velog_team11.exception.ErrorNotFoundBoardException;
import com.example.be_velog_team11.exception.ErrorNotFoundCommentException;
import com.example.be_velog_team11.exception.ErrorNotFoundUserException;
import com.example.be_velog_team11.exception.ErrorUtils.ErrorCode;
import com.example.be_velog_team11.model.Board;
import com.example.be_velog_team11.model.Comment;
import com.example.be_velog_team11.model.User;
import com.example.be_velog_team11.repository.BoardRepository;
import com.example.be_velog_team11.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public void deleteComment(Long comment_id, User loginUser) {
        Comment comment = commentRepository.findById(comment_id).orElseThrow(() -> new ErrorNotFoundCommentException(ErrorCode.ERROR_NOTFOUND_COMMENT));
        if (!comment.getUser().getId().equals(loginUser.getId()))
            throw new ErrorNotFoundUserException(ErrorCode.ERROR_NOTMATCH_COMMENT_MODIFY);

        commentRepository.delete(comment);
    }

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
