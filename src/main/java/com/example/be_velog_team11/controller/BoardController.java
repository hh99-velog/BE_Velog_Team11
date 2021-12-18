package com.example.be_velog_team11.controller;

import com.example.be_velog_team11.dto.request.BoardRequestDto;
import com.example.be_velog_team11.dto.response.BoardResponseDto;
import com.example.be_velog_team11.security.UserDetailsImpl;
import com.example.be_velog_team11.service.BoardService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BoardController {
    private final BoardService boardService;

    @ApiOperation("게시글 전체조회")
    @GetMapping("/api/boards")
    public Result<?> boardAllSearch(){
        return new Result<>(boardService.findAll());
    }

    @ApiOperation("게시글 작성")
    @PostMapping("/api/boards")
    public Long boardUpload(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestPart(value = "data") BoardRequestDto boardRequestDto,
            @RequestPart(value = "multipartFile", required = false) MultipartFile multipartFile
    ) throws IOException {
        log.info("board_write ={}",boardRequestDto);
        log.info("multipartFile={}",multipartFile.getOriginalFilename());
        return boardService.saveBoard(userDetails.getUser(), boardRequestDto, multipartFile);
    }

    @ApiOperation("게시글 상세조회")
    @GetMapping("/api/boards/detail/{board_id}")
    public BoardResponseDto boardGetDetail(@PathVariable Long board_id) {
        log.info("board_id={}",board_id);
        return boardService.findBoard(board_id);
    }

    @ApiOperation("게시글 수정")
    @PutMapping("/api/boards/detail/{board_id}")
    public void boardUpdate(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long board_id,
            @RequestPart(value = "data") BoardRequestDto boardRequestDto,
            @RequestPart(value = "multipartFile", required = false) MultipartFile multipartFile
    ) throws IOException {
        log.info("MultipartFile_update={}",multipartFile);
        log.info("board_update_data={}",boardRequestDto);;
        boardService.modifyBoard(userDetails.getUser(), board_id, boardRequestDto, multipartFile);
    }

    @ApiOperation("게시글 삭제")
    @DeleteMapping("/api/boards/detail/{board_id}")
    public void boardDelete(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long board_id
    ) {
        log.info("board_delete={}",board_id);
        boardService.deleteBoard(userDetails.getUser(), board_id);
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    static class Result<T> {
        T data;
    }
}
