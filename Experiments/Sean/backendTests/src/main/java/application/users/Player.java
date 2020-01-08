package application.users;

import javax.persistence.Entity;

/**
 * @author Sean Griffen
 */
@Entity
public class Player extends User{
	
	/**
	 * Default constructor for Player
	 */
	public Player() { super(); }
	
	/**
	 * Create User of type {@code Player} with ID {@code id}, user name {@code username}, and password {@code password}
	 * @param id
	 * 		Desired {@code id} of the User
	 * @param username
	 * 		Desired {@code username} of the User
	 * @param password
	 * 		Desired {@code password} of the User
	 */
	public Player(Long id, String username, String password) { super(Player.class, id, username, password); }
	
	/**
	 * Create User of type {@code Player} with ID {@code id}, user name {@code username}, and password {@code password}, and Location data {@code latitude}, {@code longitude}, and {@code accuracy}
	 * @param id
	 * 		Desired {@code id} of the User
	 * @param username
	 * 		Desired {@code username} of the User
	 * @param password
	 * 		Desired {@code password} of the User
	 * @param latitude
	 * 		Desired {@code latitude} component of the Location
	 * @param longitude
	 * 		Desired {@code longitude} component of the Location
	 * @param accuracy
	 * 		Desired {@code accuracy} component of the Location
	 */
	public Player(Long id, String username, String password, float latitude, float longitude, int accuracy) { super(Player.class, id, username, password, latitude, longitude, accuracy); }
}
