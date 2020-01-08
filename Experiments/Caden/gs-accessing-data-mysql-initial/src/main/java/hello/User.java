package hello;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String name;
	private String pass;
	private int    authorityLevel;
	
	public User() {
		authorityLevel = 0;
	}
	
	public User(String name, String pass) {
		this();
		this.name = name;
		this.pass = pass;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPass() {
		return pass;
	}
	
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public int getAuthorityLevel() {
		return authorityLevel;
	}

	public void setAuthorityLevel(int authorityLevel) {
		this.authorityLevel = authorityLevel;
	}

	@Override
	public String toString() {
		String s = "N/A";
		
		switch(authorityLevel) {
		case 0: s = "Player";
			break;
		case 1: s = "Host";
			break;
		case 2: s = "Admin";
			break;
		}
		
		return "User: " + name + " has permissions: " + s;
	}
}
