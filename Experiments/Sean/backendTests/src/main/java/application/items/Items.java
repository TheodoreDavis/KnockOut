package application.items;

/**
 * @author Theodore Davis
 * Declares methods all items are required to have.
 * See @Code ItemsAbs
 */

public interface Items {
	
	/**
	 * 
	 * @return id of the item
	 */
	public int getId();
	
	/**
	 * 
	 * @return URL or file path to image related to this item
	 */
	public String getURL();
	
	/**
	 * 
	 * @return Description of the item
	 */
	public String getDesc();
	
	/**
	 * 
	 * @return Cost of the item
	 */
	public int getCost();
	
}
