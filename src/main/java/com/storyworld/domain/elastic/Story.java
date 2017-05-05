package com.storyworld.domain.elastic;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.elasticsearch.annotations.Document;

import com.storyworld.domain.sql.User;
import com.storyworld.enums.StoryState;
import com.storyworld.enums.StoryType;

@Document(indexName = "story", type = "story")
public class Story {
	
	private long id;

	private String name;

	private String description;
	
	private List<String> text;

	private LocalDateTime date;

	private StoryState status;
	
	private StoryType type;

	private User author;
	
}
