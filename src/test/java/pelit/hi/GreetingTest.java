package pelit.hi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;

import pelit.hi.logic.GreetingCard;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")

public class GreetingTest {
	
	@Value("http://localhost:${server.port}${server.servlet.context-path}")	
	private String urlBase; 
		
	@Autowired
	TestRestTemplate restTemplate;//=new RestTemplate();	
	
	@Autowired
	private Logger logger;
	
	@PostConstruct
	public void init() {
		logger.info("*** Testing on url:{}", urlBase);
	}
	
	@Test
	public void httpTest() throws Exception{
		String url = urlBase+"/greet?name=alex";
		GreetingCard card=restTemplate.getForObject(url, GreetingCard.class, "alex");
		assertEquals(
				card.getName(), 
				"alex");

		// Note: if you get any errors here, try it temporarily without json conversion.
		// Sometimes errors such as 404 can be misleadingly reported as json conversion error:
		//ResponseEntity<String> str=restTemplate.getForEntity(url, String.class);
	}
}
