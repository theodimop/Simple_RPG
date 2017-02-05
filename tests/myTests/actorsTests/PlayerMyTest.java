package myTests.actorsTests;

import actors.Actor;
import actors.Enemy;
import actors.Player;
import inventory.Armor;
import inventory.Equipment;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * ----------------------------------------------------------------------------  <br>
 * PlayerMyTest.java created byTheo Dimopoulos on 26-09-2016.                          <br>
 * Email:   dimopoulosth.td@gmail.com | td41@st-andrews.ac.uk                    <br>
 * ----------------------------------------------------------------------------  <br>
 * PlayerMyTest class implements JUnit tests for Player.java method that
 * are not in Practicals UML
 *
 * @author Theo Dimopoulos
 * @version 26-09-2016
 */
public class PlayerMyTest {

    private static final String name = "Theo";
    private static final int health = 1000;
    private static final int attackPower = 100;
    private static final int defensivePower = 80;
    private static final int inventoryCapacity = 6;


    @BeforeClass // executed once before all other tests in this class
    public static void printInitial() {
        System.out.println("Starting tests on Player class.");
    }


    public Actor createTestPlayer() {
        System.out.println("setting up new testPlayer(" + name + ", "
                + health + ", " + attackPower + ", " + defensivePower + ", "
                + inventoryCapacity + ")");
        return new Player(name, health, attackPower, defensivePower, inventoryCapacity);
    }

    public Equipment createTestArmor() {
        System.out.println("creating new testArmor(Wooden Shield, 15).");
        return new Armor("Wooden Shield", 15);
    }

    public Actor createTestEnemy() {
        System.out.println("creating new testEnemy.(1000,100,80).");
        return new Enemy(health, attackPower, defensivePower);
    }

    @Test
    public void testIsEquippedSuccess() {
        Player testPlayer = (Player) createTestPlayer();
        Equipment equipment = createTestArmor();
        testPlayer.pickup(equipment);
        testPlayer.equip((Armor) equipment);

        assertTrue(testPlayer.isEquipped());
    }

    @Test
    public void testIsEquippedFailBecauseItemNotInInventory() {
        Player testPlayer = (Player) createTestPlayer();
        Equipment equipment = createTestArmor();
        testPlayer.equip((Armor) equipment);

        assertFalse(testPlayer.isEquipped());
    }

    @Test
    public void testIsEquippedFailBecauseItemDropped() {
        Player testPlayer = (Player) createTestPlayer();
        Equipment equipment = createTestArmor();
        testPlayer.pickup(equipment);
        testPlayer.equip((Armor) equipment);
        testPlayer.drop(equipment);

        assertFalse(testPlayer.isEquipped());
    }

    @Test
    public void testIsEquippedFailBecauseItemUnequipped() {
        Player testPlayer = (Player) createTestPlayer();
        Equipment equipment = createTestArmor();
        testPlayer.pickup(equipment);
        testPlayer.equip((Armor) equipment);
        testPlayer.unequipArmor();

        assertFalse(testPlayer.isEquipped());
    }

    @Test
    public void testIsEquippedFailBecauseItemOnlyPickedUp() {
        Player testPlayer = (Player) createTestPlayer();
        Equipment equipment = createTestArmor();
        testPlayer.pickup(equipment);

        assertFalse(testPlayer.isEquipped());
    }

    @Test
    public void testGetLevel() {
        Player testPlayer = (Player) createTestPlayer();

        assertEquals(1, testPlayer.getLevel());
    }

    @Test
    public void testLevelUpSuccess() {
        Player testPlayer = (Player) createTestPlayer();
        Enemy testEnemy = (Enemy) createTestEnemy();

        while (testEnemy.isAlive()) {
            testEnemy.defendAgainst(testPlayer);
        }

        assertEquals(2, testPlayer.getLevel());
    }

    @Test
    public void testLevelUpFail() {
        Player testPlayer = (Player) createTestPlayer();
        Enemy testEnemy = (Enemy) createTestEnemy();
        testPlayer.attack(testEnemy);

        assertEquals(1, testPlayer.getLevel());
    }

    @Test
    public void testPlayerReceivedNoDamageBecauseOfEquipment() {
        Player testSubject = (Player) createTestPlayer();
        Enemy testEnemy = (Enemy) createTestEnemy();

        Armor armor = new Armor("", 100);
        testSubject.getInventory().add(armor);
        testSubject.equip(armor);
        testEnemy.attack(testSubject);

        System.out.println(testSubject.getDefensivePower());
        assertEquals(1000, testSubject.getHealth());
    }

}