package com.storyworld.repository.sql;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.storyworld.domain.sql.Story;
import com.storyworld.domain.sql.User;

public interface StoryRepository extends JpaRepository<Story, Long> {

	public Page<Story> findByAuthor(User author, Pageable pageRequest);

	public Page<Story> findByName(String name, Pageable pageRequest);
}
