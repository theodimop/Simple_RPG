package inventory;

import actors.Actor;

/**
 * ----------------------------------------------------------------------------  <br>
 * inventory.Consumable.java created byTheo Dimopoulos on 19-09-2016.                      <br>
 * Email:   dimopoulosth.td@gmail.com | td41@st-andrews.ac.uk                    <br>
 * ----------------------------------------------------------------------------  <br>
 * The abstract class inventory.Consumable extends an item's behaviour by adding
 * a term of usage on it. A consumable object can be consumed by an
 * actor to other actors in the same room. After being consumed it
 * doesn't exist in the inventory.
 *
 * @author Theo Dimopoulos
 * @version 19-09-2016
 */
public abstract class Consumable extends Item {

    /**
     * inventory.Consumable Constructor, uses inheritance to pass
     * values to instance variable name.
     */
    protected Consumable(String name) {
        super(name);
    }

    /**
     * Abstract method useOn. It's implementation will be
     * provided in Class OOP.POISON and HEALTH_POTION.
     *
     * @param actor The actor who will consume consumable object.
     */
    public abstract void useOn(Actor actor);


}
