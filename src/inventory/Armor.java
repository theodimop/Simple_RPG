package inventory;

import actors.Player;

/**
 * ----------------------------------------------------------------------------  <br>
 * Armor.java created byTheo Dimopoulos on 19-09-2016.                       <br>
 * Email:   dimopoulosth.td@gmail.com | td41@st-andrews.ac.uk                    <br>
 * ----------------------------------------------------------------------------  <br>
 * Armor.java class extends the Equipment.java class. This class instances
 * are used as items to actors to increase their defense power.
 *
 * @author Theo Dimopoulos
 * @version 19-09-2016
 */
public class Armor extends Equipment {

    private int defensivePower;     //OOP.ARMOR's defensive power

    /**
     * OOP.ARMOR Constructor. It utilizes the superclass
     * constructor.
     *
     * @param name           OOP.ARMOR's name.
     * @param defensivePower OOP.ARMOR's defensive power.
     */
    public Armor(String name, int defensivePower) {
        super(name);
        this.defensivePower = defensivePower;
    }

    /**
     * Defensive power's Getter.
     *
     * @return Returns the armors defensive power.
     */
    public int getDefensivePower() {
        return defensivePower;
    }

    /**
     * Equips the player with this armor object.
     *
     * @param player The armor receiver player.
     * @return Returns true if player is equipped with
     * the armor.
     */
    @Override
    public boolean equipOn(Player player) {
        return player != null && player.equip(this);
    }
}
