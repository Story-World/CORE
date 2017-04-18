package com.storyworld.repository.elastic;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.storyworld.domain.elastic.Chat;

public interface ChatRepository extends ElasticsearchRepository<Chat, String> {

}
