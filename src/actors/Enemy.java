package actors;

import inventory.Inventory;

/**
 * ----------------------------------------------------------------------------  <br>
 * actors.Enemy.java created byTheo Dimopoulos on 19-09-2016.                       <br>
 * Email:   dimopoulosth.td@gmail.com | td41@st-andrews.ac.uk                    <br>
 * ----------------------------------------------------------------------------  <br>
 * actors.Enemy.java create objects that are enemy characters of the game.
 * An enemy is an actor with unlimited storage in its inventory.
 *
 * @author Theo Dimopoulos
 * @version 19-09-2016
 */
public class Enemy extends Actor {

    //Overloaded Constructors

    /**
     * Constructor uses the super class constructor to create object.
     *
     * @param health         Actor's health.
     * @param attackPower    Actor attack power.
     * @param defensivePower Actor defensive power.
     * @param inventory      Inventory's capacity.
     */
    public Enemy(int health, int attackPower, int defensivePower,
                 Inventory inventory) {

        super(health, attackPower, defensivePower, inventory);
    }

    /**
     * Constructor uses the super class constructor to create object.
     *
     * @param health         Actor's health.
     * @param attackPower    Actor attack power.
     * @param defensivePower Actor defensive power.
     */
    public Enemy(int health, int attackPower, int defensivePower) {
        super(health, attackPower, defensivePower, 0);
    }
}
