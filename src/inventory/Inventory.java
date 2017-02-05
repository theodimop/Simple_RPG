package inventory;

import java.util.ArrayList;
import java.util.List;


/**
 * ----------------------------------------------------------------------------  <br>
 * items.Inventory.java created byTheo Dimopoulos on 19-09-2016.                       <br>
 * Email:   dimopoulosth.td@gmail.com | td41@st-andrews.ac.uk                    <br>
 * ----------------------------------------------------------------------------  <br>
 * This class constitutes a bag for player's items. It is responsible
 * for all the actions between a player and its items.
 *
 * @author Theo Dimopoulos
 * @version 19-09-2016
 */
public class Inventory {

    private int capacity;
    private List<Item> items;

    // Overloaded Constructors

    /**
     * Construct an inventory.
     *
     * @param capacity Inventory's capacity.
     */
    public Inventory(int capacity) {
        this.capacity = capacity;
        this.items = new ArrayList<>();
    }

    /**
     * Construct an inventory.
     */
    public Inventory() {
        items = new ArrayList<>();
    }

    /**
     * Finds the number of items that are currently in the items.
     *
     * @return Returns the number of items in items.
     */
    public int getSize() {
        return items.size();
    }

    /**
     * Finds the number of items that can be stored in items.
     *
     * @return Returns the capacity of the items.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Adds an item to this inventory.
     *
     * @param item The requested to be added, item.
     * @return Returns true if there was enough space in the items and
     * false when a null items is given or when there is not enough
     * space to this items.
     */
    public boolean add(Item item) {
        if (item != null) {
            return !contains(item) && items.add(item);
        }
        return false;                       //parameter item is null,

    }

    /**
     * Adds all the items of another items.
     *
     * @param inventory The source items.
     * @return Returns true if there was enough space in the items.
     * It returns false when a null items is given or when there is not
     * enough space to this items, for all items to be stored*
     */
    public boolean addAll(Inventory inventory) {
        if (inventory != null) {
            items.addAll(inventory.items);  //Try to add all the items of an inventory
            return true;                    //All items of the items were added successfully
        }
        return false;                   //param items is null
    }

    /**
     * Removes an item from items.
     *
     * @param item The removing item
     * @return Returns true if the given item was removed
     * and false if the parameter was null or the item is
     * not in items.
     */
    public boolean remove(Item item) {
        if (item != null) {
            return items.contains(item) && items.remove(item);
        }
        return false;                   //parameter item is null
    }

    /**
     * Method overloading. Removes an item from items.
     *
     * @param index The position of the removing item in items
     * @return Returns true if the removal was successful and false
     * if the index parameter was invalid.
     */
    public boolean remove(int index) {
        if (index >= 0) {
            return index < items.size() && (items.remove(index) != null);
        }
        return false;                   //index invalid, negative index
    }

    /**
     * Search for a specific item in the items.
     *
     * @param index The key for item's search.
     * @return Returns the item if is in the items.
     * Returns null if items isn't in the items.
     */
    public Item get(int index) {
        if (index >= 0) {
            if (index < items.size()) {
                return items.get(index);
            } else {
                return null;                //index > items's capacity
            }
        }
        return null;                        //index invalid, negative index
    }

    /**
     * Check if item is in items.
     *
     * @param item The key for the search in items.
     * @return Returns true it item is contained in the items an false if not.
     */
    public boolean contains(Item item) {
        if (item != null) {
            return items.contains(item);      //Item not in inventory
        }
        return false;           //parameter item is null
    }

    /**
     * Increase the capacity of an items by an amount given
     * by parameter.
     *
     * @param increase The increment amount.
     */
    public void increaseCapacity(int increase) {
        if (increase > 0) {
            capacity += increase;           //Increase means a positive given value only
        }
    }

    //Enhancement Methods

    /**
     * Checks if inventory contains any Consumable.
     *
     * @return Returns true if there is at least one
     * consumable item in inventory
     */
    public boolean containsConsumable() {
        for (Item item : items) {
            if (item instanceof Consumable) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if inventory contains any Equipment.
     *
     * @return Returns true if there is at least one
     * equipment item in inventory
     */
    public boolean containsEquipment() {
        for (Item item : items) {
            if (item instanceof Equipment) {
                return true;
            }
        }
        return false;
    }
}
