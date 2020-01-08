package application.users;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

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
	public String addUser(User user, int type) {
		
		switch(type) {
		
			case(0):
				
				uRepo.save((Administrator) user);
				break;
			case(1):
				
				uRepo.save((Moderator) user);
				break;
			case(2):
				
				uRepo.save((Player) user);
				break;
		}
		return user.getUsername() + " added to database.";
	}
}
