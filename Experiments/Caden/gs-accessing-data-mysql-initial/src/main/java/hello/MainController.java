package hello;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import hello.User;
import hello.UserRepository;

@Controller
@RequestMapping(path="/demo")
public class MainController {
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping(path="/add")
	public @ResponseBody String addNewUser(@RequestParam String name, @RequestParam String pass) {
		User u = new User(name, pass);
		userRepository.save(u);
		return "Saved";
	}

	@GetMapping(path="/all")
	public @ResponseBody Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	@GetMapping(path="/search")
	public @ResponseBody String searchUsers(@RequestParam Integer id) {
		for(User user:userRepository.findAll()) {
			if(id != null)
				if(id == user.getId())
					return user.toString();
		}
		return "User not found";
	}
	
	@GetMapping("/login")
	public String newUser(Model model) {
		model.addAttribute("user", new User());
		return "user";
	}
	
	 
   @PostMapping("/login")
   public String userSubmit(@ModelAttribute User user) {
	   userRepository.save(user);
       return "result";
   }

}
