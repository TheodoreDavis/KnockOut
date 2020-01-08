package application.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import application.tools.UserService;
import application.users.*;

/**
 * 
 * @author Sean Griffen
 * @author Theodore Davis
 * 
 */
@RestController
@RequestMapping(path = "/users")
public class UserController {
	
	/**
	 * UserService service
	 */
	private UserService uService;
	
	/**
	 * Logger object
	 */
	private final Logger log = LoggerFactory.getLogger(MainController.class);
	
	/**
	 * 
	 * @param uService
	 */
	public UserController(UserService uService) { this.uService = uService; }
	
	/**
	 * Method for user controller connectivity testing
	 * @return
	 * 		Connectivity phrase
	 */
	@RequestMapping(value = "")
	public @ResponseBody String welcome() {
		
		log.info("User screen accessed");
		return "Welcome to the user screen of 007:Knockout";
	}
	
	/**
	 * Get all Users in database
	 * @return
	 * 		List of {@code User} objects in database
	 */
	@RequestMapping(value = "/get")
	public @ResponseBody List<User> getUsers() {
		
		List users = uService.getUsers();
	    log.info("Number of Users Fetched:" + users.size());
	    return users;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/get/{id}")
	public @ResponseBody Optional<User> getUser(@PathVariable("id") int id) {
		
		Optional user = uService.getUser(id);
		log.info("User fetched");
		return user;
	}
	
	@PostMapping(value = "/add", consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
								/*produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }*/)
	public @ResponseBody String addUser(@RequestBody Player user) {

		return uService.addUser(user);
	}
}
