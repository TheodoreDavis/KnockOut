package com.example.knockout007;




/**
 * @author Theodore Davis
 * Declares methods all items are required to have.
 * See @Code ItemsAbs
 */

public interface Item {

    /**
     *
     * @return id of this item
     */
    public int getId();

    /**
     *
     * @return location of item if on map
     */
    public Location getLocation();

    /**
     * If this returns -1, this item is on the map and avalible to be picked up
     * otherwise, if this item is owned and on the map it is active.
     * @return id of user who has possesion of this item
     */
    public int getOwner();

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

    /**
     *
     * @return class of this object
     */
    public Class<?> getType();

    /**
     *
     * @return id for the server this item is in
     */
    public int getServerId();

}
