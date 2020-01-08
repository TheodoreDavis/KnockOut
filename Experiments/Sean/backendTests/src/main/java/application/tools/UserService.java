package application.tools;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import application.users.*;

@Service
public class UserService {
	
	/**
	 * 
	 */
	private UserRepository uRepo;
	
	/**
	 * 
	 * @param uRepo
	 */
	public UserService (UserRepository uRepo) { this.uRepo = uRepo; }
	
	/**
	 * 
	 * @return
	 */
	public List<User> getUsers() { return uRepo.findAll(); }
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Optional<User> getUser(int id) { return uRepo.findById(id); }
	
	/**
	 * 
	 * @return
	 */
	public String addUser(User user) {
		
		uRepo.save(user);
		return user.getUsername() + " added to database.";
	}
}
