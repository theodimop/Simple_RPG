package myTests.gameTests;

/**
 * Created by td41 on 27/09/16.
 */

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import actors.*;
import game.*;
import inventory.*;

public class GameMapMyTest {
    private static final int WIDTH = 2;
    private static final int HEIGHT = 3;
    private static final int X1 = 1;
    private static final int Y1 = 0;

    private Enemy ENEMY_WEAK;
    private Enemy ENEMY_STRONG;
    private Player PLAYER;
    private Item ITEM;
    private Weapon WEAPON;
    private Armor ARMOR;

    public GameMap createEmptyTestSubject() {
        GameMap map = new GameMap(WIDTH, HEIGHT);
        return map;
    }

    public GameMap createTestSubject() {
        ENEMY_WEAK.pickup(WEAPON);
        ENEMY_STRONG.pickup(ARMOR);
        GameMap map = new GameMap(WIDTH, HEIGHT);
        map.generateRoom(0, 0);
        map.generateRoom(0, 1).addToInventory(ITEM);
        map.generateRoom(X1, Y1).addOccupant(ENEMY_WEAK);
        map.generateRoom(X1, Y1 + 1).addOccupant(ENEMY_STRONG);
        map.generateRoom(1, 2);
        return map;
    }

    @Before
    public void setup() {
        ENEMY_WEAK = new Enemy(1, 1, 2);
        ENEMY_STRONG = new Enemy(10, 10, 10);
        PLAYER = new Player("Link", 10, 2, 3, 3);
        ITEM = new HealthPotion("Small", 5);
        WEAPON = new Weapon("Sword", 2);
        ARMOR = new Armor("Shield", 2);
    }

    @Test
    public void testInitializeMap() {
        GameMap testSubject = new GameMap(3, 3);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertNull(testSubject.getRoom(i, j));
            }
        }

    }

}