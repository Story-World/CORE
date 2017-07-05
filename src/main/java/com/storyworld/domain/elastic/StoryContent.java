package com.storyworld.domain.elastic;

import java.util.List;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Data;

@Data
@Document(indexName = "story", type = "story")
public class StoryContent {

	@Id
	private String id;

	@NotNull
	@Length(min = 4, max = 255)
	private String title;

	private List<String> pages;

}
