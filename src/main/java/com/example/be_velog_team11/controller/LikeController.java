package com.example.be_velog_team11.controller;

import com.example.be_velog_team11.config.LoginUser;
import com.example.be_velog_team11.model.User;
import com.example.be_velog_team11.service.LikeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class LikeController {

    private final LikeService likeService;

    @ApiOperation("좋아요 등록")
    @PostMapping("/like/{boardId}")
    public ResponseEntity<String> addLike(@LoginUser User loginUser,
                                          @PathVariable Long boardId) {
        log.info("login_user_id={}",loginUser.getId());
        boolean result = false;

        result = likeService.addLike(loginUser, boardId);

        return result ?
                new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ApiOperation("좋아요 삭제")
    @DeleteMapping("/like/{boardId}")
    public ResponseEntity<String> cancelLike(@LoginUser User loginUser,
                                             @PathVariable Long boardId) {
        log.info("login_user_id={}",loginUser.getId());
        likeService.cancelLike(loginUser, boardId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("좋아요 조회, 카운트")
    @GetMapping("/like/{boardId}")
    public ResponseEntity<List<String>> getLikeCount(
            @LoginUser User loginUser,
            @PathVariable Long boardId
    ) {
        log.info("login_user_id={}",loginUser.getId());
        List<String> resultData
                = likeService.count(loginUser, boardId);

        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }
}
