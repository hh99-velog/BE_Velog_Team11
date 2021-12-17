package com.example.be_velog_team11.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponseDto {
    private Long id;
    private String title;       // 제목
    private String content;     // 내용
    private String nickname;
    private String img;
    private String createdAt;
    private Integer like;
    private List<CommentResponseDto> commentResponseDtoList;


    public BoardResponseDto(Long id, String title, String content, String nickname, String img, String createdAt, Integer like) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.nickname = nickname;
        this.img = img;
        this.createdAt = createdAt;
        this.like = like;
    }
}
