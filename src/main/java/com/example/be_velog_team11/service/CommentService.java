package com.example.be_velog_team11.service;


import com.example.be_velog_team11.dto.request.CommentRequestDto;
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



@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommentService {


    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;


    @Transactional
    public void comment(Long board_id, User user, CommentRequestDto commentRequestDto) {
        // board_id 조회
        Board find_board = boardRepository.findById(board_id).orElseThrow(
                () -> new ErrorNotFoundBoardException(ErrorCode.ERROR_BOARD_ID)
        );

        Comment comment = commentRepository.save(new Comment(commentRequestDto, user, find_board));

        // comment DB 저장
        commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long comment_id, User loginUser) {
        Comment comment = commentRepository.findById(comment_id).orElseThrow(() -> new ErrorNotFoundCommentException(ErrorCode.ERROR_NOTFOUND_COMMENT));
        if (!comment.getUser().getId().equals(loginUser.getId()))
            throw new ErrorNotFoundUserException(ErrorCode.ERROR_NOTMATCH_COMMENT_MODIFY);

        commentRepository.delete(comment);
    }

}
