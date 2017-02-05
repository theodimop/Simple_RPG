package inventory;

/**
 * ----------------------------------------------------------------------------  <br>
 * inventoryTests.Item.java created byTheo Dimopoulos on 19-09-2016.                                <br>
 * Email:   dimopoulosth.td@gmail.com | td41@st-andrews.ac.uk                             <br>
 * ----------------------------------------------------------------------------  <br>
 * inventory.Item class construct inventory.Item objects which will be picked up and used
 * by the game actors.
 *
 * @author Theo Dimopoulos
 * @version 19-09-2016
 */
public class Item {

    private String name;    //Item's name

    /**
     * Constructor for an item.
     *
     * @param name The name of the item.
     */
    public Item(String name) {
        this.name = name;
    }

    /**
     * Getter for item's name.
     *
     * @return Returns the item's name.
     */
    public String getName() {
        return name;
    }
}
