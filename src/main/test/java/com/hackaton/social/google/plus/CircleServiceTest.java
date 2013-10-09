package com.hackaton.social.google.plus;

import java.io.IOException;

import org.fest.assertions.Fail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author mdaleki
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:application-context.xml"})
public class CircleServiceTest {

	@Autowired
	private CircleService circleService;


	@Test
	public void shouldLoadCirclesForMe() {
		//given

		//when
		try {
			circleService.loadCircles("zioberm@gene.com");
		} catch (IOException e) {
			e.printStackTrace();
			Fail.fail("Should not happen");
		}

		//then
	}
}
