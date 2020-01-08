package application.users;

import javax.persistence.*;


import org.springframework.core.style.ToStringCreator;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import application.tools.Location;


/**
 * @author Sean Griffen
 */
@Entity
public abstract class User implements UserInterface {
	
	/************************************************************** START VARIABLE DECLARATIONS **************************************************************/
	
	/**
	 * ID of the {@code User}
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/**
	 * User name of the {@code User}
	 */
	private String username;
	
	/**
	 * Password of the {@code User}
	 */
	private String password;
	
	/**
	 * Current level of the {@code User}
	 */
	private int level;	
	
	/**
	 * Total number of kills of the {@code User}
	 */
	private int kills;
	
	/**
	 * Total number of deaths of the {@code User}
	 */
	private int deaths;
	
	/**
	 * Type of the {@code User}
	 */
	private Class type;
	
	/**
	 * {@code Location} of the {@code User}
	 */
	@Embedded
	private Location location;
	
	/*************************************************************** END VARIABLE DECLARATIONS ***************************************************************/
	
	/******************************************************************* START CONSTRUCTORS ******************************************************************/
	
	/**
	 * Default Constructor for User
	 */
	public User() {
		
		setLevel(1);
		setKills(0);
		setDeaths(0);
		location = new Location(0, 0, 0);
	}
	
	/**
	 * Create User of type {@code t} with ID {@code id}, user name {@code username}, and password {@code password}
	 * @param t
	 * 		Desired type {@code t} of the {@code User}
	 * @param id
	 * 		Desired {@code id} of the {@code User}
	 * @param username
	 * 		Desired {@code username} of the {@code User}
	 * @param password
	 * 		Desired {@code password} of the {@code User}
	 */
	public User(Class t, Long id, String username, String password) {
		
		setType(t);
		setId(id);
		setUsername(username);
		setPassword(password);
		setLevel(1);
		setKills(0);
		setDeaths(0);
	}
	/**
	 * Create User of type {@code t} with ID {@code id}, user name {@code username}, and password {@code password}, and {@code Location} data {@code latitude}, {@code longitude}, and {@code accuracy}
	 * @param t
	 * 		Desired type {@code t} of the {@code User}
	 * @param id
	 * 		Desired {@code id} of the {@code User}
	 * @param username
	 * 		Desired {@code username} of the {@code User}
	 * @param password
	 * 		Desired {@code password} of the {@code User}
	 * @param latitude
	 * 		Desired {@code latitude} component of the {@code Location}
	 * @param longitude
	 * 		Desired {@code longitude} component of the {@code Location}
	 * @param accuracy
	 * 		Desired {@code accuracy} component of the {@code Location}
	 */
	public User(Class t, Long id, String username, String password, float latitude, float longitude, int accuracy) {
		
		setType(t);
		setId(id);
		setUsername(username);
		setPassword(password);
		setLevel(1);
		setKills(0);
		setDeaths(0);
		
		location = new Location(latitude, longitude, accuracy);
	}
	
	/******************************************************************** END CONSTRUCTORS *******************************************************************/
	
	/***************************************************************** START GETTERS/SETTERS *****************************************************************/
	
	/**
	 * Get {@code id} of the {@code User}
	 * @return
	 * 		{@code id}
	 */
	public Long getId() { return id; }
	/**
	 * Set {@code id} of the {@code User}
	 * @param id
	 * 		Desired {@code id} of the {@code User}
	 */
	public void setId(Long id) { this.id = id; }
	
	/**
	 * Get {@code username} of the {@code User}
	 * @return
	 * 		{@code username}
	 */
	public String getUsername() { return username; }
	/**
	 * Set {@code username} of the {@code User}
	 * @param username
	 * 		Desired {@code username} of the {@code User}
	 */
	public void setUsername(String username) { this.username = username; }
	
	/**
	 * Get {@code password} of the {@code User}
	 * @return
	 * 		{@code password}
	 */
	public String getPassword() { return password; }
	/**
	 * Set {@code password} of the {@code User}
	 * @param password
	 * 		Desired {@code password} of the {@code User}
	 */
	public void setPassword(String password) { this.password = password; }	
	
	/**
	 * Get {@code level} of the {@code User}
	 * @return
	 * 		{@code level}
	 */
	public int getLevel() { return level; }
	/**
	 * Set {@code level} of the {@code User}
	 * @param level
	 * 		Desired {@code level} of the User
	 */
	public void setLevel(int level) { this.level = level; }
	
	/**
	 * Get {@code kills} of the {@code User}
	 * @return
	 * 		{@code kills}
	 */
	public int getKills() { return kills; }
	/**
	 * Set {@code kills} of the {@code User}
	 * @param kills
	 * 		Desired {@code kills} of the {@code User}
	 */
	public void setKills(int kills) { this.kills = kills; }
	
	/**
	 * Get {@code deaths} of the {@code User}
	 * @return
	 * 		{@code deaths}
	 */
	public int getDeaths() { return deaths; }
	/**
	 * Set {@code deaths} of the {@code User}
	 * @param deaths
	 * 		Desired {@code deaths} of the {@code User}
	 */
	public void setDeaths(int deaths) { this.deaths = deaths; }
	
	/**
	 * Get {@code kdRatio} of the {@code User}
	 * @return
	 * 		{@code kdRatio}
	 */
	public double getKDRatio() { return kills / deaths; }
	
	/**
	 * Get {@code type} of the {@code User}
	 * @return
	 * 		{@code type}
	 */
	public Class getType() { return type; }
	/**
	 * Set {@code type} of the {@code User}
	 * @param type
	 * 		Desired {@code type} of the {@code User}
	 */
	public void setType(Class type) { this.type = type; }
	
	/**
	 * Get {@code Location} of the {@code User}
	 * @return
	 * 		{@code Location}
	 */
	public Location getLocation() { return location; }
	
	/******************************************************************* END GETTERS/SETTERS *****************************************************************/
	
	/******************************************************************** START MISC METHODS *****************************************************************/
	
	/**
	 * Converts {@code User} to String object
	 */
	public String toString() {
		
		return new ToStringCreator(this)
				
				.append("ID", this.getId())
				.append("User name", this.getUsername())
				.append("Password", this.getPassword())
				.append("Type", this.getType())
				.append("Location", this.getLocation())
				.toString();
	}
	
	/********************************************************************* END MISC METHODS ******************************************************************/
	
	/********************************************************************** END CLASS USER *******************************************************************/
}
