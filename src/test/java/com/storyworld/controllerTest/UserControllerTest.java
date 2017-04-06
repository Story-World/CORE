package com.storyworld.controllerTest;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.storyworld.config.WebConfig;
import com.storyworld.domain.json.Request;
import com.storyworld.domain.sql.User;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class UserControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void loginTestSuccess() throws Exception {
		User user = new User();
		user.setName("RYSTGDTdasdasd");
		user.setPassword("qwerty");
		Request request = new Request();
		request.setUser(user);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(request);
		this.mockMvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(json))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.success", is(false)));
	}

	@Test
	public void loginTestError() throws Exception {
		User user = new User();
		user.setName("dsadasda111d111111111");
		user.setPassword("2222222");
		Request request = new Request();
		request.setUser(user);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(request);
		this.mockMvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(json))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
	}

	@Test
	public void getUserError() throws Exception {
		Request request = new Request();
		request.setToken("");
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(request);
		this.mockMvc.perform(post("/user/getUser").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(json))
				.andExpect(status().isForbidden())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
	}
}
