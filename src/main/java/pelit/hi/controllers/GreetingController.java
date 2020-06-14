package pelit.hi.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pelit.hi.logic.GreetService;
import pelit.hi.logic.GreetingCard;

/**
 * Main controller of this app - sends a greeting!
 *
 */
@RestController
public class GreetingController {
	
	@Autowired
	private GreetService service;

	@GetMapping(value="/greet", produces = APPLICATION_JSON_VALUE)
	public GreetingCard greeting(@RequestParam String name){
		return service.greet(name);
	}
}