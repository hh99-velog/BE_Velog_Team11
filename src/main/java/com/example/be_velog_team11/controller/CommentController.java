package com.example.be_velog_team11.controller;

import com.example.be_velog_team11.config.LoginUser;
import com.example.be_velog_team11.dto.request.CommentRequestDto;
import com.example.be_velog_team11.dto.response.CommentResponseDto;
import com.example.be_velog_team11.model.User;
import com.example.be_velog_team11.security.UserDetailsImpl;
import com.example.be_velog_team11.service.CommentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @ApiOperation(value = "댓글 전체 조회")
    @GetMapping("/api/board/detail/{board_id}/comment")
    public List<CommentResponseDto> getComments(@PathVariable Long board_id) {
        return commentService.getAllComments(board_id);
    }

    @ApiOperation(value = "댓글 작성")
    @PostMapping("api/board/comment/{board_id}")
    public void createCommnet(@PathVariable Long board_id,
                              @AuthenticationPrincipal UserDetailsImpl userDetails,
                              @RequestBody CommentRequestDto commentRequestDto) {
        commentService.comment(board_id, userDetails.getUser(), commentRequestDto);
    }

    @ApiOperation(value = "댓글 삭제")
    @DeleteMapping("/api/board/detail/{comment_id}")
    public void deleteComment(@PathVariable Long comment_id, @LoginUser User loginUser) {
        commentService.deleteComment(comment_id, loginUser);
    }
}
