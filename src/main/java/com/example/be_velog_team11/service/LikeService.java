package com.example.be_velog_team11.service;

import com.example.be_velog_team11.exception.ErrorNotFoundBoardException;
import com.example.be_velog_team11.exception.ErrorNotFoundLikeException;
import com.example.be_velog_team11.exception.ErrorUtils.ErrorCode;
import com.example.be_velog_team11.model.Board;
import com.example.be_velog_team11.model.Likes;
import com.example.be_velog_team11.model.User;
import com.example.be_velog_team11.repository.BoardRepository;
import com.example.be_velog_team11.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public boolean addLike(User loginUser, Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new ErrorNotFoundBoardException(ErrorCode.ERROR_BOARD_ID));

        if (!isNotAlreadyLike(loginUser, board)) {
            likeRepository.save(Likes.builder()
                    .user(loginUser)
                    .board(board)
                    .build());
            return true;
        }
        return false;

    }

    @Transactional
    public void cancelLike(User loginUser, Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new ErrorNotFoundBoardException(ErrorCode.ERROR_BOARD_ID));
        Likes likes = likeRepository.findByUserAndBoard(loginUser, board).orElseThrow(() -> new ErrorNotFoundLikeException(ErrorCode.ERROR_LIKE_ID));
        likeRepository.delete(likes);
    }

    public List<String> count(User loginUser, Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new ErrorNotFoundBoardException(ErrorCode.ERROR_BOARD_ID));
        Integer boardLikeCount = likeRepository.countByBoard(board).orElse(0);

        List<String> resultData = new ArrayList<>(Collections.singletonList(String.valueOf(boardLikeCount)));

        if (Objects.nonNull(loginUser)) {
            resultData.add(String.valueOf(!isNotAlreadyLike(loginUser, board)));
            return resultData;
        }
        return resultData;
    }


    private boolean isNotAlreadyLike(User loginUser, Board board) {
        return likeRepository.existsByUserAndBoard(loginUser, board);
    }
}
