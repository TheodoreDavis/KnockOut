package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.core.style.ToStringCreator;

/**
 * 
 * @author Sean Griffen
 *
 */

@Entity
@Table(name = "Users")
public class User {
	
	/**
	 * ID of the user
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	/**
	 * Username of the user
	 */
	@Column(name = "Username")
    private String name;
    
	/**
	 * Password of the user
	 */
	@Column(name = "Authenticator")
    private String password;
	
	/**
	 * 
	 */
	@Column(name = "IsAdmin")
	private boolean admin;
	
	/**
	 * Sets admin status of the user
	 * @param admin
	 *    True if admin status desired, false otherwise
	 */
	public void setAdmin(boolean admin) {
		
		this.admin = admin;
	}
	
	/**
	 * Gets if the user is an admin or not
	 * @return
	 *    True if admin, false otherwise
	 */
	public boolean isAdmin() {
		
		return admin;
	}
	
	/**
	 * Gets the ID of the user
	 * @return
	 *    ID of the user
	 */
	public Integer getID() {
		
		return id;
	}
	
	/**
	 * Sets the ID of the user
	 * @param id
	 *    ID to set to
	 */
	public void setID(Integer id) {
		
		this.id = id;
	}
	
	/**
	 * Gets the username of the user
	 * @return
	 *    Name of the user
	 */
	public String getName() {
		
		return name;
	}
	
	/**
	 * Sets the username of the user
	 * @param name
	 *    Username to set to
	 */
	public void setName(String name) {
		
		this.name = name;
	}
	
	/**
	 * Gets the password of the user
	 * @return
	 *    password
	 */
	public String getPassword() {
		
		return password;
	}
	
	/**
	 * Sets the password of the user
	 * @param password
	 *    Password to set to
	 */
	public void setPassword(String password) {
		
		this.password = password;
	}
	
	/**
	 * Converts user object to String
	 */
	@Override
	public String toString() {
		
		return new ToStringCreator(this)
				
				.append("ID", this.getID())
				.append("Admin", this.isAdmin())
				.append("Username", this.getName())
				.append("Password", this.getPassword())
				.toString();
	}
}
