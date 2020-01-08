package application.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import application.tools.MainService;

/**
 * 
 * @author Theodore Davis
 * @author Sean Griffen
 * 
 */
@RestController
@RequestMapping(path = "/")
public class MainController {
	
	/**
	 * 
	 */
	private MainService mService;
	
	/**
	 * Logger object
	 */
	private final Logger log = LoggerFactory.getLogger(MainController.class);
	
	public MainController(MainService mService) { this.mService = mService; }
	
	/**
	 * Welcome page
	 * @return
	 * 		Connectivity phrase
	 */
	@GetMapping(path = "/")
	public @ResponseBody String welcome() {
		
		log.info("Welcome screen accessed");
		return "Welcome to the backend division of 007:Knockout";
	}
}
