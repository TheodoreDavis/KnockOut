package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author Sean Griffen
 *
 */
@RestController
public class MainController {
	
	/**
	 * userRepository object
	 */
	@Autowired
	UserRepository userRepository;
	
	/**
	 * Logger object
	 */
	private final Logger log = LoggerFactory.getLogger(MainController.class);
	
	/**
	 * Welcome Page. Tells you what you can do
	 */
	@GetMapping(path = "/")
	public @ResponseBody String[] welcome() {
		
		String result[] = { "Welcome to the user database.",
							"Use /users/add to add a user.",
							"Use /users to see all users (But be sure you have an admin password).",
							"Use /user/{id} to see the info of a specific user (But be sure to have that user's password too).",
							"Use /user/change/{id} to change either the name or password of that specific user (But be sure to either have an admin password or that user's password too.)",
							"Use /clear to clear all users from the database (But be sure to have an admin password).",
							"Use /clear/{id} to delete a specific user from the database (But be sure to have either an admin password, or the soon-so-be-deleated-user's password).",							
						  };
				
		return result;
	}
	
	/**
	 * Lists all users in the database. Need to have an admin password.
	 * @param password
	 *   Database admin's password(s)
	 * @return result
	 *   List of Users
	 */
	@GetMapping(path = "/users")
	public @ResponseBody List<User> seeUsers(@RequestParam String password) {
		
		if (!(password.equals("password"))) {
			
			log.info("Request to see all users denied");
			throw new RuntimeException("Non-admins cannot see all users. Please enter correct admin password.");
		}
		
	    log.info("Request to see all users granted");
	    List<User> result = userRepository.findAll();
	    log.info("Number of Records Fetched:" + result.size());
	    return result;
	}
	
	/**
	 * Adds a user to the database. Cannot be 'admin'
	 * @param name
	 *    Name of the user to be added. Cannot be 'admin'
	 * @param password
	 *    Password for the user to be added. Cannot be blank
	 * @return
	 *    String that says that the user has been added if successful
	 */
	@GetMapping(path = "/users/add")
	public @ResponseBody String addUser(@RequestParam String name, @RequestParam String password) {
		
		if (name.equals("admin")) {
			
			log.info("Request to add user with name 'admin' denied");
			throw new RuntimeException("Cannot add a user with the name 'admin'. Please enter a new name.");
		}
		
		String passwordTrim = password.trim();
		if (passwordTrim.equals("")) {
			
			log.info("Request to add user with blank password denied");
			throw new RuntimeException("Cannot add another user with a blank password. Please enter a new password.");
		}
		
		User n = createUser(name, password);
		userRepository.save(n);
		log.info("User " + n.getName() + " with ID #" + n.getID() + " added");
		
		String result = "User " + n.getName() + " added.";
		return result;
	}

	/**
	 * Get's a user's data based on the user ID entered. Need to have either an admin password or that user's password.
	 * @param password
	 *    Admin password or password of user trying to see
	 * @param id
	 *    ID of the user to see
	 * @return
	 *    User with Id 'id'
	 */
	@GetMapping(path = "/users/{id}")
	public @ResponseBody Optional<User> getUser(@RequestParam String password, @PathVariable("id") int id) {
		
		Optional<User> results = userRepository.findById(id);
		User u = results.get();
		
		if (!(password.equals(u.getPassword()) || !(password.equals("password")))) {
			
			log.info("Request to see user #" + u.getID() + " denied");
			throw new RuntimeException("Incorrect password for user #" + u.getID() + ", or admin. Please enter correct password.");
		}
		
		log.info("Request to see user #" + id + " granted");
        return results;
	}
	
	/**
	 * Either changes a user's name or password based on the user ID entered. Need to have either admin password or that user's password
	 * @param password
	 *    Admin password or password of user trying to change
	 * @param change
	 *    What to change, enter username (or name) or password
	 * @param data
	 *    What to replace with
	 * @param id
	 *    ID of the user to change
	 * @return
	 *    String with what was changed
	 */
	@GetMapping(path = "/users/change/{id}")
	public @ResponseBody String changeUser(@RequestParam String password, @RequestParam String change, @RequestParam String data, @PathVariable int id) {
		
		String result = null;
		
		if (!(userRepository.existsById(id))) {
			
			log.info("Request to see non-existent user denied.");
		}
			
		Optional<User> users = userRepository.findById(id);
		User u = users.get();
			
		if (!(password.contentEquals(u.getPassword()))) {
			
			log.info("Request to change user #" + u.getID() + "'s info denied");
			throw new RuntimeException("Incorrect password for user #" + u.getID() + ". Please enter correct password.");
		}
		
		log.info("Request to change user #" + u.getID() + "'s info granted");
		
		switch (change) {
		
		case ("username"):
			
			if (data.equals("admin")) {
				
				log.info("Request to change user #" + u.getID() + "'s name to 'admin' denied");
				throw new RuntimeException("Cannot change username to 'admin'. Please enter a different name.");
			}
		
			String oldName = u.getName();
			u.setName(data);
			userRepository.save(u);
			if (result == null) { result = "User " + oldName + "'s name changed to " + u.getName() + "."; }
			else { result += "User " + oldName + "'s name changed to " + u.getName() + "."; }
			
			log.info("User #" + u.getID() + "'s name changed to " + u.getName());
		break;
		case ("name"):
			
			if (data.equals("admin")) {
				
				log.info("Request to change user #" + u.getID() + "'s name to 'admin' denied");
				throw new RuntimeException("Cannot change username to 'admin'. Please enter a different name.");
			}
		
			String oldName1 = u.getName();
			u.setName(data);
			userRepository.save(u);
			if (result == null) { result = "User " + oldName1 + "'s name changed to " + u.getName() + "."; }
			else { result += "User " + oldName1 + "'s name changed to " + u.getName() + "."; }
			
			log.info("User #" + u.getID() + "'s name changed to " + u.getName());
		break;
		case ("password"):
			
			String dataCrop = data.trim();
			if (dataCrop.equals("")) {
				
				log.info("Request to change password to nothing denied");
				throw new RuntimeException("Cannot change password to nothing. Please enter a password.");
			}
			
			u.setPassword(data);
			if (result == null) { result = "User " + u.getName() + "'s password changed."; }
			else { result += "User " + u.getName() + "'s password changed."; }
			
			log.info("User #" + u.getID() + "'s password changed to " + u.getName());
		break;
		}
		
		if (result == null) { result = "Nothing was changed for user " + u.getName() + "."; }
		
		log.info("User #" + u.getID() + " untouched");
		return result;
	}
	
	/**
	 * Deletes all users from the database. Need to have an admin password
	 * @param password
	 *    Admin password
	 * @return
	 *    String that says that the database was cleared if successful
	 */
	@GetMapping(path = "/clear")
	public @ResponseBody String clearDatabase(@RequestParam String password) {
		
		if (!(password.equals("password"))) {
			
			log.info("Request to clear database denied");
			throw new RuntimeException("Non-admins not allowed to clear the database. Please enter correct admin password.");
		}
		
		log.info("Request to clear database granted");
		userRepository.deleteAll();
		
		return "Database Cleared";
	}
	
	/**
	 * Deletes a specific user from the database based on the user ID. Need to have an admin password
	 * @param password
	 *    Admin password
	 * @param id
	 *    ID of user to be deleted
	 * @return
	 *    String that says that the user was deleted if successful
	 */
	@GetMapping(path = "/clear/{id}")
	public @ResponseBody String clearUser(@RequestParam String password, @PathVariable("id") int id) {
		
		Optional<User> results = userRepository.findById(id);
		User u = results.get();
		
		if (!(password.equals("password")) || !(password.equals(u.getPassword()))) {
			
			log.info("Request to delete user #" + id + " denied");
			throw new RuntimeException("Non-admins not allowed to clear the database. Please enter correct admin password.");
		}
		
		log.info("Request to delete user #" + id + " granted");
		userRepository.deleteById(id);
		
		return "User #" + id + "with name '" + u.getName() + "' removed from database";
	}
	
	/**
	 * Helper method that creates a user
	 * @param name
	 *    Name of the user
	 * @param password
	 *    Password of the user
	 * @return
	 *    A user with name 'name' and password 'password'
	 */
	private User createUser(String name, String password) {
		
		User u = new User();
		u.setName(name);
		u.setPassword(password);
		
		return u;
	}
}
