package com.storyworld.repository.sql;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.storyworld.domain.sql.Story;
import com.storyworld.enums.StoryState;

public interface StoryRepository extends JpaRepository<Story, Long> {

	public Page<Story> findByStateIn(StoryState state, Pageable page);
	
}
