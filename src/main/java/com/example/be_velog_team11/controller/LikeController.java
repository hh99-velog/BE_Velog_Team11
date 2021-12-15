package com.example.be_velog_team11.controller;

import com.example.be_velog_team11.config.LoginUser;
import com.example.be_velog_team11.model.User;
import com.example.be_velog_team11.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/like/{boardId}")
    public ResponseEntity<String> addLike(@LoginUser User loginUser,
                                          @PathVariable Long boardId) {
        boolean result = false;

        if (Objects.nonNull(loginUser))
            result = likeService.addLike(loginUser, boardId);

        return result ?
                new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/like/{boardId}")
    public ResponseEntity<String> cancelLike(@LoginUser User loginUser,
                                             @PathVariable Long boardId) {
        if (loginUser != null)
            likeService.cancelLike(loginUser, boardId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/like/{boardId}")
    public ResponseEntity<List<String>> getLikeCount(
            @LoginUser User loginUser,
            @PathVariable Long boardId
    ) {
        List<String> resultData
                = likeService.count(loginUser, boardId);

        return new ResponseEntity<>(resultData, HttpStatus.OK);
    }
}
