package com.storyworld.service.impl;

import javax.persistence.PersistenceException;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.storyworld.domain.json.Request;
import com.storyworld.domain.json.Response;
import com.storyworld.domain.sql.Story;
import com.storyworld.domain.sql.User;
import com.storyworld.enums.StoryState;
import com.storyworld.repository.sql.StoryRepository;
import com.storyworld.repository.sql.UserRepository;
import com.storyworld.service.StoryService;

@Service
public class StoryServiceImpl implements StoryService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private StoryRepository storyRepository;

	private static final Logger LOG = LoggerFactory.getLogger(StoryServiceImpl.class);

	@Override
	public void addStory(Request request, Response response) {
		User user = userRepository.findByToken(request.getToken());
		Story story = request.getStory();
		story.setAuthor(user);
		story.setState(StoryState.NEW);
		try {
			storyRepository.save(story);
		} catch (PersistenceException e) {
			LOG.error(e.toString());
			if (e.getCause() instanceof ConstraintViolationException)
				LOG.info("UNIQUE VALUE");
		} catch (Exception e) {
			LOG.error(e.toString());
		}
	}

	@Override
	public void saveStory() {
		// storyRepository.save(story);
	}

}
