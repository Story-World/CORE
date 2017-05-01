package com.storyworld.domain.elastic;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "comment", type = "comment")
public class CommentContent {

	@Id
	private String id;

	@NotNull
	private String content;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "CommentContent [id=" + id + ", content=" + content + "]";
	}

}
