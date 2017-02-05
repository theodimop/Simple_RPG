package myTests.gameTests;

import actors.Enemy;
import actors.Player;
import game.Game;
import game.GameMap;
import inventory.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * ----------------------------------------------------------------------------  <br>
 * GameMyTest.java created byTheo Dimopoulos on 12-10-2016.                                <br>
 * Email:   dimopoulosth.td@gmail.com | td41@st-andrews.ac.uk                             <br>
 * ----------------------------------------------------------------------------  <br>
 * This JUnit test test the enhancement method in game.java class
 *
 * @author Theo Dimopoulos
 * @version 12-10-2016
 */
public class GameMyTest {
    private GameMap map;
    private Player player;
    private Enemy enemy;
    private Weapon weapon;
    private Armor armor;
    private HealthPotion potion;
    private Poison poison;

    private Game createTestSubject() {
        player = new Player("Link", 10, 10, 10, 10);
        weapon = new Weapon("Knife", 8);
        enemy = new Enemy(100, 8, 4);
        armor = new Armor("Shirt", 2);
        potion = new HealthPotion("Large", 8);
        poison = new Poison("Small", 5);
        player.pickup(poison);
        map = new GameMap(2, 3);
        map.generateRoom(0, 0);
        map.getRoom(0, 0).addToInventory(weapon);
        map.generateRoom(0, 1);
        map.getRoom(0, 1).addOccupant(player);
        map.getRoom(0, 1).addOccupant(enemy);
        map.getRoom(0, 1).addToInventory(potion);
        map.generateRoom(1, 0);
        return new Game(map, player);
    }

    @Test
    public void testEmptyActorsInventory() {
        Game testSubject = createTestSubject();
        enemy.getInventory().add(new Item("An Item"));

        while (enemy.isAlive()) {
            testSubject.fight(player, enemy);
        }
        assertEquals(enemy.getInventory().getSize(), 0);
    }
}
