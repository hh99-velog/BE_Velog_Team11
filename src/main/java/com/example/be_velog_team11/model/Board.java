package com.example.be_velog_team11.model;

import com.example.be_velog_team11.dto.request.BoardRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Board extends Timestamped {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column
    private String img;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "board" , cascade = CascadeType.ALL)
    private final List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "board" , cascade = CascadeType.ALL)
    private final Set<Likes> likes = new HashSet<>();

    // 게시글 수정 (제목, 내용, 이미지)
    public void update(BoardRequestDto boardRequestDto, String imgUrl) {
        this.title = boardRequestDto.getTitle();
        this.content = boardRequestDto.getContent();
        if (imgUrl != "") {
            this.img = imgUrl;
        }
    }
}
