package com.storyworld.repository.elastic;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.storyworld.domain.elastic.Chat;

public interface ChatRepository extends ElasticsearchRepository<Chat, String> {
	
	public List<Chat> findByTitle(String title);

}
