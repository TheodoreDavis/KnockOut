package application.users;

import application.tools.Location;

public interface UserInterface {
	
	/**
	 * Get {@code id} of the {@code User}
	 * @return
	 * 		{@code id}
	 */
	public Long getId();
	/**
	 * Set {@code id} of the {@code User}
	 * @param id
	 * 		Desired {@code id} of the {@code User}
	 */
	public void setId(Long id);
	
	/**
	 * Get {@code username} of the {@code User}
	 * @return
	 * 		{@code username}
	 */
	public String getUsername();
	/**
	 * Set {@code username} of the {@code User}
	 * @param username
	 * 		Desired {@code username} of the {@code User}
	 */
	public void setUsername(String username);
	
	/**
	 * Get {@code password} of the {@code User}
	 * @return
	 * 		{@code password}
	 */
	public String getPassword();
	/**
	 * Set {@code password} of the {@code User}
	 * @param password
	 * 		Desired {@code password} of the {@code User}
	 */
	public void setPassword(String password);	
	
	/**
	 * Get {@code level} of the {@code User}
	 * @return
	 * 		{@code level}
	 */
	public int getLevel();
	/**
	 * Set {@code level} of the {@code User}
	 * @param level
	 * 		Desired {@code level} of the User
	 */
	public void setLevel(int level);
	
	/**
	 * Get {@code kills} of the {@code User}
	 * @return
	 * 		{@code kills}
	 */
	public int getKills();
	/**
	 * Set {@code kills} of the {@code User}
	 * @param kills
	 * 		Desired {@code kills} of the {@code User}
	 */
	public void setKills(int kills);
	
	/**
	 * Get {@code deaths} of the {@code User}
	 * @return
	 * 		{@code deaths}
	 */
	public int getDeaths();
	/**
	 * Set {@code deaths} of the {@code User}
	 * @param deaths
	 * 		Desired {@code deaths} of the {@code User}
	 */
	public void setDeaths(int deaths);
	
	/**
	 * Get {@code kdRatio} of the {@code User}
	 * @return
	 * 		{@code kdRatio}
	 */
	public double getKDRatio();
	
	/**
	 * Get {@code type} of the {@code User}
	 * @return
	 * 		{@code type}
	 */
	public Class getType();
	/**
	 * Set {@code type} of the {@code User}
	 * @param type
	 * 		Desired {@code type} of the {@code User}
	 */
	public void setType(Class type);
	
	/**
	 * Get {@code Location} of the {@code User}
	 * @return
	 * 		{@code Location}
	 */
	public Location getLocation();
}
