package myTests.gameTests;

import actors.Enemy;
import actors.Player;
import game.Room;
import inventory.Inventory;
import inventory.Item;
import inventory.Poison;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.HashSet;

import static org.junit.Assert.*;

/**
 * ----------------------------------------------------------------------------  <br>
 * RoomMyTest.java created byTheo Dimopoulos on 12-09-2016.                      <br>
 * Email:   dimopoulosth.td@gmail.com | td41@st-andrews.ac.uk                    <br>
 * ----------------------------------------------------------------------------  <br>
 * This class is a JUnit Test for Room.java methods that are not in
 * practicals UML.
 *
 * @author Theo Dimopoulos
 * @version 12-09-2016
 */
public class RoomMyTest {

    private Item ITEM;
    private Player PLAYER;

    public Room createTestSubject() {
        return new Room(new Inventory(), null);
    }

    public Collection createTestCollectionOfFiveEnemies() {
        Collection enemiesCollection = new HashSet<Enemy>();

        for (int i = 0; i < 5; i++) {
            enemiesCollection.add(new Enemy(100, 50, 30));
        }
        return enemiesCollection;
    }

    public Room createTestSubjectWithInventory() {
        Inventory inv = new Inventory();
        inv.add(ITEM);
        return new Room(inv, null);
    }

    public Inventory createInventoryWithOneItem() {
        Inventory inventory = new Inventory();
        inventory.add(new Item("Dummy Item"));
        return inventory;
    }

    @Before
    public void setup() {
        ITEM = new Poison("Small Poison", 1);
        PLAYER = new Player("Link", 10, 10, 10, 10);
    }

    @Test
    public void testGetEnemyOccupants() {
        Room testSubject = createTestSubjectWithInventory();
        Collection<Enemy> testEnemies = createTestCollectionOfFiveEnemies();
        testEnemies.forEach(testSubject::addOccupant);

        assertEquals(testEnemies, testSubject.getEnemyOccupants());
    }

    @Test
    public void testGetInventoryByComparingInventorySize() {
        Room testSubject = createTestSubject();
        Inventory testInventory = createInventoryWithOneItem();
        testSubject.addToInventory(testInventory);

        assertEquals(1, testSubject.getInventory().getSize());
    }

}
