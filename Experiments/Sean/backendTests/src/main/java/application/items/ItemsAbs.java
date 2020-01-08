/**
 * 
 */
package application.items;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Theodore Davis
 * Item abstract for use mostly in the store view to make a nice iterable of the Items interface
 */

@Entity
@Table(name = "Items")
public abstract class ItemsAbs implements Items{

	/**
	 * key for finding items in the item table
	 * automatically generated
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID")
	private Integer id;
	
	/**
	 * Location of the image for this item
	 */
	@Column(name = "URL")
	public String imageURL;
	
	/**
	 * Description of the item
	 */
	@Column(name = "Description")
	public String description;

	/**
	 * cost of the item
	 */
	@Column(name = "Cost")
	public int cost;
	
	/******************************************************************************************************************
	 * End of variables
	 * 
	 * Start of Methods
	 ******************************************************************************************************************/
	
	/**
	 * Creates new item with respective parameters
	 * @param url String to find location of image data
	 * @param desc String to describe the object
	 * @param cost int for how muh item cost
	 */
	public ItemsAbs(String url, String desc, int cost) {
		this.imageURL = url;
		this.description = desc;
		this.cost = cost;
	}
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getURL() {
		return imageURL;
	}

	@Override
	public String getDesc() {
		return description;
	}

	@Override
	public int getCost() {
		return cost;
	}
	
	
	
}
