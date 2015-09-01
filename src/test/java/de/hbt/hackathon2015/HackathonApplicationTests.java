package de.hbt.hackathon2015;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.hbt.hackathon2015.Hackathon2015Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Hackathon2015Application.class)
@WebAppConfiguration
public class HackathonApplicationTests {

	@Test
	public void contextLoads() {
	}

}
