package com.storyworld.repository.elastic;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.storyworld.domain.elastic.CommentContent;

public interface CommentContentRepository extends ElasticsearchRepository<CommentContent, String> {

}
