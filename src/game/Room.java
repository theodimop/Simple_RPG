package game;

import actors.Actor;
import actors.Enemy;
import inventory.Inventory;
import inventory.Item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ----------------------------------------------------------------------------  <br>
 * game.Room.java created byTheo Dimopoulos on 19-09-2016.                            <br>
 * Email:   dimopoulosth.td@gmail.com | td41@st-andrews.ac.uk                    <br>
 * ----------------------------------------------------------------------------  <br>
 * This class construct game.Room objects which will be inserted to map available
 * slots.
 *
 * @author Theo Dimopoulos
 * @version 19-09-2016
 */
public class Room {

    private Inventory inventory;            //game.Room inventory
    private Collection<Actor> occupants;    //game.Room Occupants

    // Overloaded Constructors

    /**
     * Construct game.Room.
     *
     * @param inventory Room's inventory
     * @param occupants Room's collection of actors.
     */
    public Room(Inventory inventory, Collection occupants) {

        this.inventory = inventory;
        if (occupants == null) {
            this.occupants = new ArrayList<>();
        } else {
            this.occupants = occupants;
        }
    }

    /**
     * Construct a room.
     *
     * @param inventory Room's inventory
     */
    public Room(Inventory inventory) {
        this.inventory = inventory;
        occupants = new ArrayList<>();
    }

    /**
     * Construct room.
     */
    public Room() {
        inventory = new Inventory();
        occupants = new ArrayList<>();
    }

    /**
     * Adds an item to room's inventory.
     *
     * @param item The requested for adding item.
     * @return Returns true if the item is added
     * in room's inventory. False if item is null.
     */
    public boolean addToInventory(Item item) {
        return item != null && inventory.add(item);
    }

    /**
     * Adds all the inventory items to room's inventory.
     *
     * @param inventory The requested for adding inventory.
     * @return Returns true if inventory item's are added
     * in room's inventory. False if inventory is null
     */
    public boolean addToInventory(Inventory inventory) {
        return inventory != null && this.inventory.addAll(inventory);
    }

    /**
     * Removes an item from room's inventory.
     *
     * @param item The requested for removing item.
     * @return Returns true if the item is removed
     * from room's inventory, returns false if item
     * is not in inventory or null.
     */
    public boolean removeFromInventory(Item item) {
        return item != null && inventory.remove(item);
    }

    /**
     * Checks if inventory contains an item.
     *
     * @param item The item for the inventory search.
     * @return Returns true if item is contained in
     * room's inventory, otherwise it returns false.
     */
    public boolean inventoryContains(Item item) {
        return item != null && inventory.contains(item);
    }

    /**
     * Adds an actor into room. An actor has to know his location
     * so we set the Room for the actor and add him in the
     * room occupants. A room cannot accommodate the same actor
     * twice.
     *
     * @param character Actor who is going to be added to room.
     * @return Returns true if actor added to room.
     */
    public boolean addOccupant(Actor character) {
        if (character != null) {
            return !occupants.contains(character) && occupants.add(character);
        }
        return false;
    }

    /**
     * Removes an actor from room. An actor has to know his location
     * so we set the Room for the actor and add him in the
     * room occupants.
     *
     * @param character Actor who is going to be removed from room.
     * @return Returns true if actor is removed from room, false
     * if actor not in room, or null.
     */
    public boolean removeOccupant(Actor character) {
        if (character != null) {
            if (this.contains(character)) {
                //character.setCurrentRoom(null);
                occupants.remove(character);
                return true;
            } else {
                return false;   //character not in room
            }
        }
        return false;
    }

    /**
     * Checks if room contains an actor.
     *
     * @param character The character for the search.
     * @return Returns true if room contains actor.
     */
    public boolean contains(Actor character) {
        return character != null && occupants.contains(character);
    }

    /*My methods, not in practical's UML.*/

    /**
     * Auxiliary method to get room occupants of
     * type enemy.
     *
     * @return Returns the collection of currently
     * existed, enemy occupants in room.
     */
    public Collection getEnemyOccupants() {
        return occupants.stream().
                filter(a -> a instanceof Enemy).
                collect(Collectors.toCollection(HashSet::new));
    }

    /**
     * Auxiliary method to get room's inventory.
     *
     * @return Returns room's inventory
     */
    public Inventory getInventory() {
        return inventory;
    }
}
