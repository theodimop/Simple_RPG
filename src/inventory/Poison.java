package inventory;

import actors.Actor;

/**
 * ----------------------------------------------------------------------------  <br>
 * Poison.java created byTheo Dimopoulos on 27-09-2016.                          <br>
 * Email:   dimopoulosth.td@gmail.com | td41@st-andrews.ac.uk                    <br>
 * ----------------------------------------------------------------------------  <br>
 * inventory.Poison.java represents an item that can be used on an actor and
 * reduce their health points.
 *
 * @author Theo Dimopoulos
 * @version 27-09-2016
 */
public class Poison extends Consumable {

    private int poisonPower;

    /**
     * Construct Poison.
     *
     * @param name        Poison name.
     * @param poisonPower The power of the poison.
     */
    public Poison(String name, int poisonPower) {
        super(name);
        this.poisonPower = poisonPower;
    }

    /**
     * Getter for poison's power.
     *
     * @return Returns the poisons power.
     */
    public int getPoisonPower() {
        return poisonPower;
    }

    /**
     * Implements the action of using a poison to
     * an actor.
     *
     * @param actor Poisoned actor.
     */
    @Override
    public void useOn(Actor actor) {
        if (actor != null) {
            actor.heal(-poisonPower);
        }
    }
}
