package com.example.be_velog_team11.service;

import com.example.be_velog_team11.exception.ErrorNotFoundCommentException;
import com.example.be_velog_team11.exception.ErrorNotFoundUserException;
import com.example.be_velog_team11.exception.ErrorUtils.ErrorCode;
import com.example.be_velog_team11.model.Comment;
import com.example.be_velog_team11.model.User;
import com.example.be_velog_team11.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public void deleteComment(Long comment_id, User loginUser) {
        Comment comment = commentRepository.findById(comment_id).orElseThrow(() -> new ErrorNotFoundCommentException(ErrorCode.ERROR_NOTFOUND_COMMENT));
        if (!comment.getUser().getId().equals(loginUser.getId()))
            throw new ErrorNotFoundUserException(ErrorCode.ERROR_NOTMATCH_COMMENT_MODIFY);

        commentRepository.delete(comment);
    }
}
