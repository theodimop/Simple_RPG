package inventory;

import actors.Player;

/**
 * ----------------------------------------------------------------------------  <br>
 * inventory.Equipment.java created byTheo Dimopoulos on 27-09-2016.        <br>
 * Email:   dimopoulosth.td@gmail.com | td41@st-andrews.ac.uk                    <br>
 * ----------------------------------------------------------------------------  <br>
 * Equipment.java is an abstract class that create objects
 * that can be used on player. Class Weapon.java and Armor.java
 * are the only subclasses of this class.
 *
 * @author Theo Dimopoulos
 * @version 27-09-2016
 */
public abstract class Equipment extends Item {

    /**
     * Construct Equipment. It will be invoked only
     * through subclasses.
     *
     * @param name Equipment's name.
     */
    public Equipment(String name) {
        super(name);
    }

    /**
     * Abstract method equipOn. The implementation will
     * be provided by subclasses.
     *
     * @param player The player that will be equipped.
     * @return Returns true if player gets equipped.
     */
    public abstract boolean equipOn(Player player);

}
