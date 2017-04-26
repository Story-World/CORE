package com.storyworld.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.storyworld.domain.json.Request;
import com.storyworld.domain.json.Response;
import com.storyworld.domain.sql.Story;
import com.storyworld.domain.sql.User;
import com.storyworld.repository.sql.StoryRepository;
import com.storyworld.repository.sql.CommentRepository;
import com.storyworld.repository.sql.UserRepository;
import com.storyworld.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private StoryRepository storyRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public void get(Request request, Response response) {
		// TODO Auto-generated method stub
	}

	@Override
	public void save(Request request, Response response) {
		User user = userRepository.findByToken(request.getToken());
		if (user != null) {
			Story stroy = storyRepository.findOne(request.getStory().getId());
		}
	}

	@Override
	public void update(Request request, Response response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Request request, Response response) {
		// TODO Auto-generated method stub

	}

}
