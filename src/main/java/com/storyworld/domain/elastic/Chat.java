package com.storyworld.domain.elastic;

import org.springframework.data.annotation.Id;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "chatest", type = "chat")
public class Chat {

	@Id
	private String id;

	@Field(type = FieldType.String)
	private String title;

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "Chat [id=" + id + ", title=" + title + "]";
	}

}
