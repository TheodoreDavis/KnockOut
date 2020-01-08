package hello;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ComsController {
	
	@Autowired
	private NameRepository Repository;
	
	@RequestMapping("/all")
    public @ResponseBody Iterable<NameObj> getAll() {
        return Repository.findAll();
    }
	
	@GetMapping("/search")
    public @ResponseBody NameObj search(@RequestParam String name) {
         for(NameObj user : Repository.findAll()) {
        	 if(name.equals(user.getName()))
        		 return user;
         }
         return null;
    }
	
	@GetMapping(path="/add")
	public @ResponseBody String addNewUser(@RequestParam String name, @RequestParam String pass,
											@RequestParam String contact, @RequestParam String country) {
		NameObj u = new NameObj(name, pass, contact, country);
		Repository.save(u);
		return "Saved";
	}
	
}
