package myTests.inventoryTests;

import inventory.*;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * ----------------------------------------------------------------------------  <br>
 * InventoryMyTest.java created byTheo Dimopoulos on 12-10-2016.                                <br>
 * Email:   dimopoulosth.td@gmail.com | td41@st-andrews.ac.uk                             <br>
 * ----------------------------------------------------------------------------  <br>
 *
 * @author Theo Dimopoulos
 * @version 12-10-2016
 */
public class InventoryMyTest {
    private static final int CAPACITY = 3;
    protected static final Item POISON = new Poison("Small Poison", 1);
    protected static final Item POTION = new HealthPotion("Small Potion", 1);
    protected static final Item ARMOR = new Armor("Small armor", 1);
    protected static final Item WEAPON = new Weapon("Small weapon", 1);

    private Inventory createTestSubject() {
        return new Inventory(CAPACITY);
    }

    @Test
    public void testContainsConsumableSuccess() {
        Inventory inventory = createTestSubject();
        inventory.add(POISON);

        assertTrue(inventory.containsConsumable());
    }

    @Test
    public void testContainsConsumableFail() {
        Inventory inventory = createTestSubject();
        inventory.add(ARMOR);

        assertFalse(inventory.containsConsumable());
    }


    @Test
    public void testContainsEquipmentSuccess() {
        Inventory inventory = createTestSubject();
        inventory.add(WEAPON);

        assertTrue(inventory.containsEquipment());
    }

    @Test
    public void testContainsEquipmentFails() {
        Inventory inventory = createTestSubject();
        inventory.add(POTION);

        assertFalse(inventory.containsEquipment());
    }

}

