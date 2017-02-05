package myTests.inventoryTests;

import static org.junit.Assert.*;

import actors.Actor;
import actors.Player;
import inventory.Armor;
import inventory.Item;
import inventory.Poison;
import inventory.Weapon;
import org.junit.Before;
import org.junit.Test;


/**
 * Created by td41 on 27/09/16.
 */
public class EquipmentTest {

    protected static final int HEALTH = 10;
    protected static final int ATTACK = 2;
    protected static final int DEFENCE = 3;
    protected static final Item ITEM = new Poison("Small Poison", 1);
    private static final String NAME = "Link";
    private static final int CAPACITY = 2;
    private Weapon WEAPON;
    private Weapon WEAPON2;
    private Armor ARMOR;
    private Armor ARMOR2;

    protected Actor createTestSubject() {
        return new Player(NAME, HEALTH, ATTACK, DEFENCE, CAPACITY);
    }

    protected Actor createTestSubject(int health, int attack, int defence) {
        return new Player(NAME, health, attack, defence, CAPACITY);
    }

    protected Actor createTestSubjectWithInventory() {
        Player p = new Player(NAME, HEALTH, ATTACK, DEFENCE, CAPACITY);
        p.pickup(ITEM);
        return p;
    }

    @Before
    public void setup() {
        WEAPON = new Weapon("Sword", 2);
        WEAPON2 = new Weapon("Sword", 2);
        ARMOR = new Armor("Shield", 3);
        ARMOR2 = new Armor("Armor", 4);
    }

    @Test
    public void TestPickUpArmorContainedIntoInventory() {
        Actor testSubject = createTestSubject();
        testSubject.pickup(ARMOR);
        assertTrue(testSubject.getInventory().contains(ARMOR));
    }
}
