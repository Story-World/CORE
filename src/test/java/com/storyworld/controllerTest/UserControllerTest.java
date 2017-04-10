package com.storyworld.controllerTest;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import org.hamcrest.core.IsNull;
import org.junit.After;
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
import com.storyworld.domain.sql.Mail;
import com.storyworld.domain.sql.MailToken;
import com.storyworld.domain.sql.Role;
import com.storyworld.domain.sql.User;
import com.storyworld.repository.sql.MailReposiotory;
import com.storyworld.repository.sql.MailTokenRepository;
import com.storyworld.repository.sql.RoleRepository;
import com.storyworld.repository.sql.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfig.class)
public class UserControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private MailReposiotory mailReposiotory;

	@Autowired
	private MailTokenRepository mailTokenRepository;

	private User user = new User();

	private Request request = new Request();

	private ObjectMapper mapper = new ObjectMapper();

	private String json;

	private Role role = new Role();

	private Set<Role> roles = new HashSet<>();

	private Set<MailToken> mailToken;

	private Mail mail;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		user.setMail("test@test.pl");
		user.setName("test123");
		user.setPassword("test123");
		user = userRepository.save(user);
	}

	@Test
	public void loginTestSuccess() throws Exception {
		request.setUser(user);
		json = mapper.writeValueAsString(request);
		mockMvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(json))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.success", is(true))).andExpect(jsonPath("$.user.token", IsNull.notNullValue()));
	}

	@Test
	public void loginTestBlockUser() throws Exception {
		user.setPassword("123");
		request.setUser(user);
		json = mapper.writeValueAsString(request);
		for (int i = 0; i < 5; i++)
			mockMvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(json))
					.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
					.andExpect(jsonPath("$.success", is(false)));
		user.setPassword("test123");
		request.setUser(user);
		json = mapper.writeValueAsString(request);
		mockMvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(json))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.success", is(false)));
	}

	@Test
	public void loginTestWithWrongPassword() throws Exception {
		user.setPassword("2222222");
		request.setUser(user);
		json = mapper.writeValueAsString(request);
		mockMvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(json))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.success", is(false)));
	}

	@Test
	public void loginTestWithWrongName() throws Exception {
		user.setName("dawsa");
		request.setUser(user);
		json = mapper.writeValueAsString(request);
		mockMvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(json))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.success", is(false)));
	}

	@Test
	public void loginTestError() throws Exception {
		user.setName("dawsa");
		user.setPassword("dawsa");
		request.setUser(user);
		json = mapper.writeValueAsString(request);
		mockMvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(json))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.success", is(false)));
	}

	@Test
	public void getUserMyseflTestForbidden() throws Exception {
		request.setToken("123");
		json = mapper.writeValueAsString(request);
		mockMvc.perform(post("/user/getUser").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(json))
				.andExpect(status().isForbidden())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
	}

	@Test
	public void getUserMyseflTestSuccess() throws Exception {
		request.setUser(user);
		json = mapper.writeValueAsString(request);
		mockMvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(json))
				.andExpect(status().isOk()).andExpect(jsonPath("$.success", is(true)))
				.andExpect(jsonPath("$.user.token", IsNull.notNullValue()));
		user = userRepository.findByName("test123");
		request.setToken(user.getToken());
		json = mapper.writeValueAsString(request);
		mockMvc.perform(post("/user/getUser").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(json))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.user", IsNull.notNullValue()));
	}

	@Test
	public void getUserOtherTestSuccess() throws Exception {
		request.setUser(user);
		json = mapper.writeValueAsString(request);
		mockMvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(json))
				.andExpect(status().isOk()).andExpect(jsonPath("$.success", is(true)))
				.andExpect(jsonPath("$.user.token", IsNull.notNullValue()));
		user = userRepository.findByName("test123");
		role = roleRepository.findOne(2L);
		roles.add(role);
		user.setRoles(roles);
		userRepository.save(user);
		request.setToken(user.getToken());
		user = userRepository.findOne(10L);
		request.setUser(user);
		json = mapper.writeValueAsString(request);
		mockMvc.perform(post("/user/getUser").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(json))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.success", is(true))).andExpect(jsonPath("$.user", IsNull.notNullValue()));
	}

	@Test
	public void getUserOtherTestForbidden() throws Exception {
		request.setUser(user);
		json = mapper.writeValueAsString(request);
		mockMvc.perform(post("/user/login").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(json))
				.andExpect(status().isOk()).andExpect(jsonPath("$.success", is(true)))
				.andExpect(jsonPath("$.user.token", IsNull.notNullValue()));
		user = userRepository.findByName("test123");
		request.setToken(user.getToken());
		user = userRepository.findOne(10L);
		request.setUser(user);
		json = mapper.writeValueAsString(request);
		mockMvc.perform(post("/user/getUser").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(json))
				.andExpect(status().isForbidden())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
	}

	@Test
	public void registerTestSuccess() throws Exception {
		userRepository.delete(user);
		request.setUser(user);
		json = mapper.writeValueAsString(request);
		mockMvc.perform(post("/user/register").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(json))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.success", is(true)));
	}

	// @Test
	public void registerTestError() throws Exception {
		request.setUser(user);
		json = mapper.writeValueAsString(request);
		mockMvc.perform(post("/user/register").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(json))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.success", is(true)));
		mockMvc.perform(post("/user/register").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(json))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.success", is(false)));
	}

	@After
	public void deleteTestUser() {
		user = userRepository.findByName("test123");
		if (user.getRoles() != null) {
			user.setRoles(null);
			userRepository.save(user);
		}
		mail = mailReposiotory.findByUser(user);
		if (mail != null)
			mailReposiotory.delete(mail);
		mailToken = mailTokenRepository.findByUser(user);
		if (mailToken != null || !mailToken.isEmpty())
			mailTokenRepository.delete(mailToken);
		userRepository.delete(user);
	}
}
