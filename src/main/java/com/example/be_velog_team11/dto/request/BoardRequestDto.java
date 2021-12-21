package com.example.be_velog_team11.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequestDto {

    @NotBlank
    private String title;       // 제목

    @NotBlank
    private String content;     // 내용
}
