package com.example.be_velog_team11.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardRequestDto {
    private String title;       // 제목
    private String content;     // 내용
}
