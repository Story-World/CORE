package com.storyworld.domain.elastic;

import java.time.LocalDateTime;

import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "comment", type = "comment")
public class Comment {

	private LocalDateTime date;

	private String content;

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
