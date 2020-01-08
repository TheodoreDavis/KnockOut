package hello;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class NameObj {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
    private String name;
    private String password;
    private String contact;
    private String country;
    
    public NameObj() {}

    public NameObj(String name, String password, String contact, String country) {
        this.name = name;
        this.password = password;
        this.contact = contact;
        this.country = country;
    }
    
    public Integer getId() {
    	return id;
    }

    public String getName() {
        return name;
    }

	public String getPassword() {
		return password;
	}

	public String getContact() {
		return contact;
	}

	public String getCountry() {
		return country;
	}
    
    
}
