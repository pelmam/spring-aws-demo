package pelit.hi.logic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GreetService {
	
	@Value("${greet.message}")
	private String message;
	
	public GreetingCard greet(String name) {
		return GreetingCard.builder()
				.name(name)
				.remarks("Remark:"+message)
				.luckyNumber((int)(1000*Math.random()))
				.build();
	}
}
