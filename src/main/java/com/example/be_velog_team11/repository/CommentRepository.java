package com.example.be_velog_team11.repository;

import com.example.be_velog_team11.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
