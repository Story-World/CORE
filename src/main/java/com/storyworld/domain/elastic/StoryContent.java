package com.storyworld.domain.elastic;

import java.util.List;

import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "story", type = "story")
public class StoryContent {

	@Id
	private String id;

	private String title;

	private List<String> pages;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getPages() {
		return pages;
	}

	public void setPages(List<String> pages) {
		this.pages = pages;
	}

	public String getId() {
		return id;
	}
}
