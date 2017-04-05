package com.storyworld.controllerTest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.storyworld.controller.HomeController;

public class HomeControllerTest {

	@Test
	public void greetingTest() {
		HomeController controller = new HomeController();
		assertEquals("welcome", controller.greeting());
	}

}
