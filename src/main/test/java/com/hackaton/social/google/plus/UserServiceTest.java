package com.hackaton.social.google.plus;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.api.services.admin.directory.model.User;

/**
 * @author mdaleki
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:application-context.xml"})
public class UserServiceTest {
	private static final Logger log = LoggerFactory.getLogger(UserServiceTest.class);

	@Autowired
	private UserService userService;

	@Test
	public void shouldGetUser() {
		//given

		//when
		final User user = userService.getUser("104435939536800300374");
		log.info("Found user {}", user.getPrimaryEmail());
		//then
	}


}
