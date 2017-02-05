package inventory;

import actors.Player;

/**
 * ----------------------------------------------------------------------------  <br>
 * OOP.WEAPON.java created byTheo Dimopoulos on 27-09-2016.                          <br>
 * Email:   dimopoulosth.td@gmail.com | td41@st-andrews.ac.uk                    <br>
 * ----------------------------------------------------------------------------  <br>
 * Weapon.java class extends the Equipment.java class. This class instances
 * are used as items to actors to increase their attack power.
 *
 * @author Theo Dimopoulos
 * @version 27-09-2016
 */
public class Weapon extends Equipment {

    private int attackPower;

    /**
     * Construct a weapon.
     *
     * @param name        Weapon's name.
     * @param attackPower Weapon's attack power.
     */
    public Weapon(String name, int attackPower) {
        super(name);
        this.attackPower = attackPower;
    }

    /**
     * Getter for weapon's attack power.
     *
     * @return Returns weapon's attack power.
     */
    public int getAttackPower() {
        return attackPower;
    }

    /**
     * Equips the player with this weapon object.
     *
     * @param player The weapon receiver player.
     * @return Returns true if player is equipped with
     * the weapon.
     */
    @Override
    public boolean equipOn(Player player) {
        return player != null && player.equip(this);
    }


}
