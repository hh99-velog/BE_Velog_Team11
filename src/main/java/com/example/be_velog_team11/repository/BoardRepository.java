package com.example.be_velog_team11.repository;

import com.example.be_velog_team11.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
