package com.example.be_velog_team11.repository;


import com.example.be_velog_team11.model.Board;
import com.example.be_velog_team11.model.Likes;
import com.example.be_velog_team11.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes,Long> {
    boolean existsByUserAndBoard(User user, Board board);

    Optional<Likes> findByUserAndBoard(User loginUser, Board board);

    Optional<Integer> countByBoard(Board board);
}
