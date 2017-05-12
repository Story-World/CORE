package com.storyworld.domain.elastic;

import java.util.List;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "story", type = "story")
public class StoryContent {

	@Id
	private String id;

	@NotNull
	@Length(min = 4, max = 255)
	private String title;

	private List<String> pages;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	@Override
	public String toString() {
		return "StoryContent [id=" + id + ", title=" + title + ", pages=" + pages + "]";
	}

}
