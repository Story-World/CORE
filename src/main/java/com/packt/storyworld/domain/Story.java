package com.packt.storyworld.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "storyworld")
public class Story {
	
	@Id
	private String id;
	
}
