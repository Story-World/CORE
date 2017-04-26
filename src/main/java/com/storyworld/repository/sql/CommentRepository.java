package com.storyworld.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;

import com.storyworld.domain.sql.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
