package com.example.be_velog_team11.controller;

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
