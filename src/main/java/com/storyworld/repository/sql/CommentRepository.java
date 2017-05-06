package com.storyworld.repository.sql;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.storyworld.domain.sql.Comment;
import com.storyworld.domain.sql.Story;
import com.storyworld.domain.sql.User;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	public Page<Comment> findByStory(Story story, Pageable pageRequest);

	public Comment findByAuthorAndStory(User author, Story story);

	//public Set<Comment> findByAuthor(User author);
	
	public Comment findByAuthor(User author);
	
	public Comment findBy_id(String _id);
}
