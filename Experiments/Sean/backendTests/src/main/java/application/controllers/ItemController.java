package application.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import application.items.ItemsAbs;
import application.tools.ItemService;

/**
 * 
 * @author Theodore Davis
 * @author Sean Griffen
 *
 */
@RestController
@RequestMapping(path = "/items")
public class ItemController {

	/**
	 * UserService service
	 */
	private ItemService iService;
	
	/**
	 * Logger object
	 */
	private final Logger log = LoggerFactory.getLogger(MainController.class);
	
	/**
	 * 
	 * @param uService
	 */
	public ItemController(ItemService iService) { this.iService = iService; }
	
	/**
	 * Method for item controller connectivity testing
	 * @return
	 * 		Connectivity phrase
	 */
	@GetMapping(path = "/")
	public @ResponseBody String welcome() {
		
		log.info("Item screen accessed");
		return "Welcome to the item screen of 007:Knockout";
	}
	
	/**
	 * Get all Users in database
	 * @return
	 * 		List of {@code User} objects in database
	 */
	@GetMapping(path = "/get")
	public @ResponseBody List<ItemsAbs> getAllUsers() {
		
	    List<ItemsAbs> items = iService.getItems();
	    log.info("Number of Users Fetched:" + items.size());
	    return items;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(path = "/get/{id}")
	public @ResponseBody Optional<ItemsAbs> getItem(@PathVariable("id") int id) {
		
		Optional item = iService.getItem(id);
		log.info("User fetched");
		return item;
	}
}
