package inventory;

import actors.Actor;

/**
 * ----------------------------------------------------------------------------  <br>
 * OOP.HEALTH_POTION.java created byTheo Dimopoulos on 19-09-2016.                    <br>
 * Email:   dimopoulosth.td@gmail.com | td41@st-andrews.ac.uk                    <br>
 * ----------------------------------------------------------------------------  <br>
 * HealthPotion.java represents an item that can be consumed and increase
 * health points of an actor.
 *
 * @author Theo Dimopoulos
 * @version 19-09-2016
 */
public class HealthPotion extends Consumable {

    private int healingPower;

    /**
     * Construct a HealthPotion.
     *
     * @param name         Potion's name.
     * @param healingPower Potion's healing power.
     */
    public HealthPotion(String name, int healingPower) {
        super(name);
        this.healingPower = healingPower;
    }

    /**
     * Getter for potion's healing power.
     *
     * @return Returns the power of the potion.
     */
    public int getHealingPower() {
        return healingPower;
    }

    /**
     * Implements the consume and usage of a healthpotion
     * to an actor.
     *
     * @param actor The deceiver actor.
     */
    @Override
    public void useOn(Actor actor) {
        if (actor != null) {
            actor.heal(healingPower);
        }
    }
}
