package com.example.be_velog_team11.controller;

import com.example.be_velog_team11.config.LoginUser;
import com.example.be_velog_team11.dto.response.CommentResponseDto;
import com.example.be_velog_team11.model.User;
import com.example.be_velog_team11.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @DeleteMapping("/api/board/detail/{comment_id}")
    public void deleteComment(@PathVariable Long comment_id, @LoginUser User loginUser) {
        commentService.deleteComment(comment_id, loginUser);
    }

    @GetMapping("/api/board/detail/{board_id}/comment")
    public List<CommentResponseDto> getComments(@PathVariable Long board_id) {
        return commentService.getAllComments(board_id);

    }
}
