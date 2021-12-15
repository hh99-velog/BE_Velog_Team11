package com.example.be_velog_team11.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponseDto {
    private Long id;          // 게시글 ID
    private String title;           // 제목
    private String content;         // 내용
    private String img;             // 이미지
    private String nickname;        // 닉네임
    private LocalDateTime createdAt;// 생성날짜
    private int like;              // 좋아요 수
}
