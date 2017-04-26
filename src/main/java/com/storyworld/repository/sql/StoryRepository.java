package com.storyworld.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;

import com.storyworld.domain.sql.Story;

public interface StoryRepository extends JpaRepository<Story, Long> {

}
