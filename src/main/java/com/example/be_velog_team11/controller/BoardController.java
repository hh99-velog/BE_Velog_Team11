package com.example.be_velog_team11.controller;

import com.example.be_velog_team11.dto.BoardRequestDto;
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
        System.out.println("1");
        System.out.println(userDetails.getUser());
        System.out.println(userDetails.getUser().getUsername());
        System.out.println(boardRequestDto.getTitle());
        System.out.println(boardRequestDto.getContent());
        boardService.saveBoard(userDetails.getUser(), boardRequestDto, multipartFile);
    }
}
