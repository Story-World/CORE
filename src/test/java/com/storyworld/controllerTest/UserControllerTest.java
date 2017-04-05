package com.storyworld.controllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.storyworld.config.WebConfig;
import com.storyworld.domain.json.Request;
import com.storyworld.domain.sql.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { WebConfig.class })
@WebAppConfiguration
public class UserControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void loginTest() throws Exception {
		User user = new User();
		user.setName("dsadasda111111111111");
		user.setPassword("2222222");
		Request request = new Request();
		request.setUser(user);
		mockMvc.perform(post("user/login").contentType(MediaType.APPLICATION_JSON_VALUE).content(request.toString()))
				.andExpect(status().is4xxClientError());
	}
}
