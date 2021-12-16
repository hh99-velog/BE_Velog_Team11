package com.example.be_velog_team11.controller;

import com.example.be_velog_team11.dto.response.CommentResponseDto;
import com.example.be_velog_team11.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/api/board/detail/{board_id}/comment")
    public List<CommentResponseDto> getComments(@PathVariable Long board_id) {
        return commentService.getAllComments(board_id);
    }
}
