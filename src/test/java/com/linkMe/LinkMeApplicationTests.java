package com.linkMe;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LinkMeApplicationTests {

	@Test
	void contextLoads() {
		LinkMeApplication linkMeApplication = new LinkMeApplication();
		assertNotNull(linkMeApplication);
	}
}