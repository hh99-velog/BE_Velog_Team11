package com.example.be_velog_team11.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
public class CommentResponseDto {
    private String nickname;
    private String content;
    private String createdAt;
}