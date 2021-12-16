package com.example.be_velog_team11.dto.response;


import com.example.be_velog_team11.model.Likes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}
