package actors;

import inventory.Armor;
import inventory.Item;
import inventory.Weapon;

/**
 * ----------------------------------------------------------------------------  <br>
 * actors.Player.java created byTheo Dimopoulos on 19-09-2016.                   <br>
 * Email:   dimopoulosth.td@gmail.com | td41@st-andrews.ac.uk                    <br>
 * ----------------------------------------------------------------------------  <br>
 * actors.Player.java is the player character of the game.
 *
 * @author Theo Dimopoulos
 * @version 19-09-2016
 */
public class Player extends Actor {

    private static final double LEVEL_UP_INCREMENT = 0.1;

    private String name;
    private Weapon equippedWeapon;  //The Weapon that player currently use
    private Armor equippedArmor;    //The Armor that player currently use
    private int level;          // Player's level


    /**
     * Construct Player object.
     *
     * @param name              Player's name.
     * @param health            Actor's max health power.
     * @param attackPower       Actor's attack power.
     * @param defensivePower    Actor's defensive power.
     * @param inventoryCapacity Actor's Inventory's max number of items
     */
    public Player(String name, int health, int attackPower, int defensivePower,
                  int inventoryCapacity) {

        super(health, attackPower, defensivePower, inventoryCapacity);
        this.name = name;
        this.level = 1;     //init player level
    }

    /**
     * Getter for player's name attribute.
     *
     * @return Returns player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter  for player's name attribute.
     *
     * @param name The new Player's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    //Method Overloading

    /**
     * This methods equips the player with
     * the weapon that is given as a parameter.
     *
     * @param weapon The weapon that player will be equipped.
     * @return Returns true, if the weapon is equipped.
     * False if not, because it doesn't exist in
     * inventory.
     */
    public boolean equip(Weapon weapon) {
        if (weapon != null) {
            if (inventoryContains(weapon)) {   //equip only if you don't currently use  another one
                equippedWeapon = weapon;
                return true;
            } else {
                return false;               //Another weapon in use...
            }
        }
        return false;
    }

    /**
     * This methods equips the player with
     * the weapon that is given as a parameter.
     *
     * @param armor The armor that player will be equipped.
     * @return Returns true, if the armor is equipped.
     * False if not, because it doesn't exist in
     * inventory.
     */
    public boolean equip(Armor armor) {
        if (armor != null) {
            if (inventoryContains(armor)) {   //equip only if you don't currently use another one
                equippedArmor = armor;
                return true;
            } else {
                return false;               //Another weapon in use...
            }
        }
        return false;
    }

    /**
     * Unequip the armor for player and
     * stores it, in inventory.
     *
     * @return Returns true if armor is unequipped.
     */
    public boolean unequipArmor() {
        if (equippedArmor != null) {
            equippedArmor = null;
            return true;            //OOP.ARMOR unequipped
        } else {
            return false;           //No armor to unequip
        }
    }

    /**
     * Getter for the armor that player
     * is equipped with.
     *
     * @return If player is equipped with an Armor
     * it returns it, otherwise returns null.
     */
    public Armor getEquippedArmor() {
        if (equippedArmor != null) {
            return equippedArmor;
        }
        return null;        //CAUTION null will be return if no armor is on
    }

    /**
     * Unequip the weapon from player and
     * stores it, in inventory.
     *
     * @return Returns true if armor is unequipped.
     */
    public boolean unequipWeapon() {
        if (equippedWeapon != null) {
            equippedWeapon = null;
            return true;            //OOP.WEAPON unequipped
        } else {
            return false;           //No weapon to unequip
        }
    }

    /**
     * Getter for the weapon that player
     * is equipped with.
     *
     * @return If player is equipped with an Weapon
     * it returns it, otherwise returns null.
     */
    public Weapon getEquippedWeapon() {
        if (equippedWeapon != null) {
            return equippedWeapon;
        }
        return null;        //CAUTION null will be return if no weapon is on
    }

    /**
     * Getter for the player's current attack
     * power.
     *
     * @return Returns player's current attack power.
     */
    @Override
    public int getAttackPower() {
        if (equippedWeapon != null) {
            return super.getAttackPower() + equippedWeapon.getAttackPower();
        } else {
            return super.getAttackPower();
        }
    }

    /**
     * Getter for the player's current armor
     * power.
     *
     * @return Returns player's current armor power.
     */
    @Override
    public int getDefensivePower() {
        if (equippedArmor != null) {
            return super.getDefensivePower() + equippedArmor.getDefensivePower();
        } else {
            return super.getDefensivePower();
        }
    }

    /**
     * Implements the drop of an item in players
     * inventory.
     *
     * @return Returns true if item was dropped.
     * False if not, because it doesn't exist in
     * inventory.
     */
    @Override
    public boolean drop(Item item) {
        if (item != null) {

            if (item.equals(equippedArmor)) {
                equippedArmor = null;
            } else if (item.equals(equippedWeapon)) {
                equippedWeapon = null;
            }
            return super.drop(item);
        }
        return false;
    }

    //Enhancement methods, not in UML

    /**
     * Getter for players level.
     *
     * @return Returns player's level.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Method increases attack and defense power
     * and inventories capacity by 10 %.
     */
    public void levelUp() {
        level++;
        int oldAttackPower = attackPower;
        int oldDefensivePower = defensivePower;
        int oldCapacity = capacity;

        attackPower += (int) Math.round(attackPower * LEVEL_UP_INCREMENT);
        defensivePower += (int) Math.round(defensivePower * LEVEL_UP_INCREMENT);
        capacity += (int) Math.round(capacity * LEVEL_UP_INCREMENT);

        //Ensure that when player levels up, all its stats increase.
        if (oldAttackPower == attackPower) {
            attackPower++;
        }
        if (oldDefensivePower == defensivePower) {
            defensivePower++;
        }
        if (oldCapacity == capacity) {
            capacity++;
        }

        getInventory().increaseCapacity(capacity - oldCapacity);
    }

    /**
     * Checks if player is wears an equipments.
     *
     * @return Returns true if player is equipped with
     * any kind of equipment.
     */
    public boolean isEquipped() {
        return equippedArmor != null || equippedWeapon != null;
    }
}
