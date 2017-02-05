package actors;

import inventory.Inventory;
import inventory.Item;

/**
 * ----------------------------------------------------------------------------  <br>
 * actors.Actor.java created byTheo Dimopoulos on 19-09-2016.                    <br>
 * Email:   dimopoulosth.td@gmail.com | td41@st-andrews.ac.uk                    <br>
 * ----------------------------------------------------------------------------  <br>
 * actors.Actor.java create game Actor instances. Concrete classes
 * actors.Enemy.java and actors.Player.java inherit this class.
 *
 * @author Theo Dimopoulos
 * @version 19-09-2016
 */
public abstract class Actor {

    protected int health;
    protected int attackPower;
    protected int defensivePower;
    protected Inventory inventory;
    protected int capacity;

    //Overloaded Constructors

    /**
     * Construct Actor.
     *
     * @param health         Actor health.
     * @param attackPower    Actor attack power.
     * @param defensivePower Actor defensive power.
     * @param inventory      Actor Inventory
     */
    public Actor(int health, int attackPower, int defensivePower, Inventory inventory) {
        this.health = health;
        this.attackPower = attackPower;
        this.defensivePower = defensivePower;
        this.inventory = inventory;
        this.capacity = inventory.getSize();
    }

    /**
     * Construct Actor.
     *
     * @param health         Actor health.
     * @param attackPower    Actor attack power.
     * @param defensivePower Actor defensive power.
     * @param capacity       Inventory's capacity.
     */
    public Actor(int health, int attackPower, int defensivePower, int capacity) {
        this(health, attackPower, defensivePower, new Inventory());
        this.inventory = new Inventory(capacity);
        this.capacity = capacity;
    }

    /**
     * Getter for actor's health.
     *
     * @return Returns the actor's health.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Method that acts as a flag for checking
     * if actor is alive.
     *
     * @return Returns true if actor's health is above 0
     */
    public boolean isAlive() {
        return health > 0;
    }

    /**
     * This method is interacts with actor's health, when
     * he receives a health potion or a poison. If
     * parameter > 0 then is healing else is poison
     *
     * @param powerOfHealOrPoison The amount of heal or damage
     */
    public void heal(int powerOfHealOrPoison) {
        this.health += powerOfHealOrPoison;
    }

    /**
     * Implements the hit to the defender.
     * After checking that defender is alive.
     *
     * @param character The defender actor.
     */
    public void attack(Actor character) {
        if (character != null) {
            if (character instanceof Player) {
                Player p = (Player) character;
                if (attackPower >= p.getDefensivePower()) {
                    p.heal(-attackPower);
                }
            } else {
                if (attackPower >= character.getDefensivePower()) {
                    character.heal(-attackPower);
                }
            }

        }
    }

    /**
     * The defender defends against the attacker.
     * If defender has more power than attacker,
     * then no damage is taken. If the defender
     * dies from a player actor, player levels up.
     *
     * @param character the defender actor.
     */
    public void defendAgainst(Actor character) {
        if (character != null) {
            if (defensivePower <= character.getAttackPower()) {         //Check if defender stronger than attacker
                health -= character.getAttackPower();
                if (!isAlive()) {    //If the defender died from player
                    this.health = 0;
                    ((Player) character).levelUp();                     // Player is leveled up
                }
            }
        }
    }

    /**
     * Getter for actor's attack power.
     *
     * @return Returns actor's attack power.
     */
    public int getAttackPower() {
        return attackPower;
    }

    /**
     * Getter for actor's defensive power.
     *
     * @return Returns actors attack power.
     */
    public int getDefensivePower() {
        return defensivePower;
    }

    /**
     * Getter for Actor inventory.
     *
     * @return Returns actor inventory.
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Implements an item's pick by an actor.
     * An enemy can pickup an item regardless
     * its inventory capacity, contrary to player
     * which has an inventory fixed capacity.
     *
     * @param item The potential pick up item
     * @return Returns true if item was pick up.
     */
    public boolean pickup(Item item) {
        if (item != null) {                                                     //if item not null
            if (this instanceof Enemy) {                                        //Enemies inventories may dont have  capacity
                if (inventory.add(item)) {                                      //Add the item
                    if (inventory.getCapacity() < inventory.getSize()) {        //Extend capacity if needed ONLY FOR ENEMIES

                        inventory.increaseCapacity(inventory.getSize() - inventory.getCapacity());
                    }
                    return true;                 //item pick up successfull
                }
            } else {
                return inventory.getSize() < inventory.getCapacity()  //Check player's inventory capacity.
                        && inventory.add(item);
            }
        }
        return false;          //param item was null
    }

    /**
     * Method implements an items drop. An item is
     * dropped only if is contained in inventory.
     * When it is drop, is added to actor's currentRoom
     * inventory.
     *
     * @param item The dropped item
     * @return Returns true if item dropped successfully.
     */
    public boolean drop(Item item) {
        if (item != null) {
            if (inventoryContains(item)) {
                inventory.remove(item);             //Remove from actor
                return true;                        //item dropped successfully
            }
        }
        return false;                    //param item was null
    }

    /**
     * Implements a test to inventory to specify
     * if an item is contained.
     *
     * @param item The key for the search item.
     * @return Returns true if item is contained to inventory
     */
    public boolean inventoryContains(Item item) {
        return item != null && inventory.contains(item);
    }

}
