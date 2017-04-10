package com.storyworld.service.impl;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.storyworld.domain.json.FavouritePlaces;
import com.storyworld.domain.json.Message;
import com.storyworld.domain.json.Request;
import com.storyworld.domain.json.Response;
import com.storyworld.domain.json.StatusMessage;
import com.storyworld.domain.sql.ConfigComponent;
import com.storyworld.domain.sql.User;
import com.storyworld.enums.ConfigComponentType;
import com.storyworld.repository.sql.ConfigComponentRepository;
import com.storyworld.repository.sql.UserRepository;
import com.storyworld.service.UserInterfaceService;

@Service
public class UserInterfaceServiceImpl implements UserInterfaceService {

	@Autowired
	private AuthorizationServiceImpl authorizationService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ConfigComponentRepository configComponentRepository;

	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public Response getFavouritePlaces(Request request, Response response) {
		User user = authorizationService.checkAccessToUser(request.getToken());
		if(user!=null){
			ConfigComponent config = getConfigUp(user);

			List<FavouritePlaces> favouritePlaces = null;
			try {
				favouritePlaces = mapper.readValue(config.getValue(), new TypeReference<List<FavouritePlaces>>() {
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
			response.setUser(user);
			response.setFavouritePlaces(favouritePlaces);
			response.setSuccess(true);
		}else{
			response.setSuccess(false);
		}
		
		return response;
	}

	@Override
	public Response saveFavouritePlaces(Request request, Response response) {
		User user = authorizationService.checkAccessToUser(request.getToken());
		ConfigComponent config = configComponentRepository.findByUserId(user.getId());
		if (config != null) {
			updateFavouritePlaces(user, request.getFavouritePlaces(), config);
			response.setMessage(new Message(StatusMessage.SUCCESS, "favouritePlacesUpdated"));
		} else {
			createFavouritePlaces(user, request.getFavouritePlaces());
			response.setMessage(new Message(StatusMessage.SUCCESS, "favouritePlacesCreated"));
		}
		response.setSuccess(true);
		return response;
	}

	private ConfigComponent getConfigUp(User user) {
		ConfigComponent config = configComponentRepository.findByUserId(user.getId());
		if (config == null) {
			User systemUser = userRepository.findByName("SYSTEM_USER");
			config = configComponentRepository.findByUserId(systemUser.getId());
		}
		return config;
	}

	private void updateFavouritePlaces(User user, List<FavouritePlaces> favouritePlaces, ConfigComponent config) {
		if (favouritePlaces.size() > 0) {
			try {
				config.setValue(mapper.writeValueAsString(favouritePlaces));
				configComponentRepository.save(config);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			configComponentRepository.delete(config);
		}
	}

	private void createFavouritePlaces(User user, List<FavouritePlaces> favouritePlaces) {
		ConfigComponent config = new ConfigComponent();
		config.setType(ConfigComponentType.FAVOURITE_PLACES);
		config.setUser(user);
		try {
			config.setValue(mapper.writeValueAsString(favouritePlaces));
		} catch (IOException e) {
			e.printStackTrace();
		}
		configComponentRepository.save(config);
	}

}
