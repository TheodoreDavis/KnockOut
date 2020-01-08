package com.example.knockout007;




    /**
     * Location object for objects that need them
     * @author Sean Griffen
     */

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
        public Location() {

            setLatitude(0);
            setLongitude(0);
            setAccuracy(0);
        }

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
         * Update (@code Location) by coping values in the param
         * @param location
         * 		Desired {@code location}
         */
        public void setLocation(Location location) {
            this.longitude = location.longitude;
            this.latitude  = location.latitude;
        }

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
         *
         * @param location other location to use
         * @return distance between this location and another
         */
        public double getDistance(Location location) {
            //d=2*asin(sqrt((sin((lat1-lat2)/2))^2 + cos(lat1)*cos(lat2)*(sin((lon1-lon2)/2))^2))
            //formula using no idea if it works or not


            return (2 * Math.asin( Math.sqrt(Math.pow(Math.sin((latitude-location.latitude) / 2), 2) +
                    Math.cos(latitude)* Math.cos(location.latitude) *
                            Math.pow((Math.sin((longitude - location.longitude) / 2)), 2))));
        }



        /********************************************************************* END MISC METHODS ******************************************************************/

        /******************************************************************** END CLASS LOCATION *****************************************************************/
    }


