package com.example.be_velog_team11.controller;

import com.example.be_velog_team11.dto.BoardRequestDto;
import com.example.be_velog_team11.dto.BoardResponseDto;
import com.example.be_velog_team11.security.UserDetailsImpl;
import com.example.be_velog_team11.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/api/boards")
    public void boardUpload(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestPart(value = "data") BoardRequestDto boardRequestDto,
            @RequestPart(value = "multipartFile", required = false) MultipartFile multipartFile
    ) throws IOException {
        System.out.println(userDetails.getUser());
        boardService.saveBoard(userDetails.getUser(), boardRequestDto, multipartFile);
    }

    @GetMapping("/api/boards/detail/{board_id}")
    public BoardResponseDto boardGetDetail(@PathVariable Long board_id) {
        return boardService.findBoard(board_id);
    }

    @PutMapping("/api/boards/detail/{board_id}")
    public void boardUpdate(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long board_id,
            @RequestPart(value = "data") BoardRequestDto boardRequestDto,
            @RequestPart(value = "multipartFile", required = false) MultipartFile multipartFile
    ) throws IOException {
        boardService.modifyBoard(userDetails.getUser(), board_id, boardRequestDto, multipartFile);
    }

    @DeleteMapping("/api/boards/detail/{board_id}")
    public void boardDelete(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long board_id
    ) {
        boardService.deleteBoard(userDetails.getUser(), board_id);
    }
}
