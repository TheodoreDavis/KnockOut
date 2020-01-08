package application.tools;

import javax.persistence.*;

import org.springframework.core.style.ToStringCreator;

@Embeddable
public class Location {
	
	/************************************************************** START VARIABLE DECLARATIONS **************************************************************/
	
	/**
	 * Latitude {@code Location} component of the {@code User}
	 */
	private float latitude;
	
	/**
	 * Longitude {@code Location} component of the {@code User}
	 */
	private float longitude;
	
	/**
	 * {@code Location} accuracy component of the {@code User}
	 */
	private int accuracy;
	
	/*************************************************************** END VARIABLE DECLARATIONS ***************************************************************/
	
	/******************************************************************* START CONSTRUCTORS ******************************************************************/
	
	/**
	 * Default constructor for Location
	 */
	public Location() { }
	
	/**
	 * Create the {@code Location} of the {@code User}
	 * @param latitude
	 * 		Desired {@code latitude} component of the {@code Location}
	 * @param longitude
	 * 		Desired {@code longitude} component of the {@code Location}
	 * @param accuracy
	 * 		Desired {@code accuracy} component of the {@code Location}
	 */
	public Location(float latitude, float longitude, int accuracy) {
		
		setLatitude(latitude);
		setLongitude(longitude);
		setAccuracy(accuracy);
	}
	
	/******************************************************************** END CONSTRUCTORS *******************************************************************/
	
	/**
	 * Update {@code Location} data of the {@code User}
	 * @param latitude
	 * 		Desired {@code latitude} component of the {@code Location}
	 * @param longitude
	 * 		Desired {@code longitude} component of the {@code Location}
	 * @param accuracy
	 * 		Desired {@code accuracy} component of the {@code Location}	
	 */
	private void setLocation(float latitude, float longitude, int accuracy) {
		
		setLatitude(latitude);
		setLongitude(longitude);
		setAccuracy(accuracy);
	}
	
	/***************************************************************** START GETTERS/SETTERS *****************************************************************/
	
	/**
	 * Get {@code latitude} component of the {@code Location}
	 * @return
	 * 		{@code latitude}
	 */
	public float getLatitude() { return latitude; }
	/**
	 * Set {@code latitude} component of the {@code Location}
	 * Update reference in {@code User}
	 * @param latitude
	 * 		Desired {@code latitude} component of the {@code Location}
	 */
	public void setLatitude(float latitude) { this.latitude = latitude; }
	
	/**
	 * Get {@code longitude} component of the {@code Location}
	 * @return
	 * 		{@code longitude}
	 */
	public float getLongitude() { return longitude; }
	/**
	 * Set {@code longitude} component of the {@code Location}
	 * Update reference in {@code User}
	 * @param longitude
	 * 		Desired {@code longitude} component of the {@code Location}
	 */
	public void setLongitude(float longitude) { this.longitude = longitude; }
	
	/**
	 * Get {@code accuracy} component of the {@code Location}. 
	 * @return
	 * 		{@code accuracy}
	 */
	public int getAccuracy() { return accuracy; }
	/**
	 * Set {@code accuracy} component of the {@code Location}
	 * Update reference in {@code User}
	 * @param accuracy
	 * 		Desired {@code accuracy} component of the {@code Location}
	 */
	public void setAccuracy(int accuracy) { this.accuracy = accuracy; }
	
	/******************************************************************* END GETTERS/SETTERS *****************************************************************/
	
	/******************************************************************** START MISC METHODS *****************************************************************/
	
	/**
	 * Converts {@code Location} object to String object
	 */
	public String toString() {
		
		return new ToStringCreator(this)
				
				.append("Latitude", this.getLatitude())
				.append("Longitude", this.getLongitude())
				.append("Accuracy", this.getAccuracy())
				.toString();
	}
	
	/********************************************************************* END MISC METHODS ******************************************************************/
	
	/******************************************************************** END CLASS LOCATION *****************************************************************/
}
