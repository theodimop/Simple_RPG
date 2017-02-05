package gui;

import actors.Enemy;
import inventory.Item;
import inventory.Weapon;
import inventory.Armor;
import inventory.Poison;
import inventory.HealthPotion;

import java.util.*;


/**
 * ----------------------------------------------------------------------------  <br>
 * GameElementsGenerator.java created byTheo Dimopoulos on 06-10-2016.                                <br>
 * Email:   dimopoulosth.td@gmail.com | td41@st-andrews.ac.uk                             <br>
 * ----------------------------------------------------------------------------  <br>
 *
 * @author Theo Dimopoulos
 * @version 06-10-2016
 */
public class GameElementsGenerator {


    private static final int ENEMY_LEVEL_ONE_HEALTH = 200;
    private static final int ENEMY_LEVEL_TWO_HEALTH = 350;
    private static final int ENEMY_LEVEL_THREE_HEALTH = 450;
    private static final int ENEMY_LEVEL_ONE_ATTACK_POWER = 40;
    private static final int ENEMY_LEVEL_TWO_ATTACK_POWER = 60;
    private static final int ENEMY_LEVEL_THREE_ATTACK_POWER = 140;
    private static final int ENEMY_LEVEL_ONE_DEFENSE_POWER = 50;
    private static final int ENEMY_LEVEL_TWO_DEFENSE_POWER = 100;
    private static final int ENEMY_LEVEL_THREE_DEFENSE_POWER = 110;


    /**
     * Enumeration Class for the different kind of items.
     * Implements method for getting a random category
     * item
     */
    private enum ItemType {
        HEALTH_POTION, POISON, ARMOR, WEAPON;

        private static final List<ItemType> ITEM_TYPES = Collections.unmodifiableList(Arrays.asList(values()));
        private static final int SIZE = ItemType.values().length;
        private static final Random RANDOM = new Random();

        /**
         * Implementation of getting a random category.
         *
         * @return Returns a random Item type.
         */
        private static ItemType random() {
            return ITEM_TYPES.get(RANDOM.nextInt(SIZE));
        }
    }

    /**
     * Random Item generator. For every item category
     * generate a random item. Number of overall Items
     * is specified by the map dimensions.
     *
     * @param numberOfItems Number Of Items.
     * @return Returns the gameTests's item list
     * @see ItemType class.
     */
    public static Collection<Item> generateItems(int numberOfItems) {
        Random r = new Random(97);
        if (numberOfItems > 0) {
            Collection<Item> gameItems = new Stack<>();
            int power;
            for (int i = 0; i < numberOfItems; i++) {

                switch (ItemType.random()) {
                    case HEALTH_POTION:
                        power = (r.nextInt(3) + 1) * 100;
                        gameItems.add(new HealthPotion("HealthPotion (" + power + ")", power));
                        break;
                    case POISON:
                        power = (r.nextInt(8) + 1) * 20;
                        gameItems.add(new Poison("Poison (" + power + ")", power));
                        break;
                    case ARMOR:
                        power = (r.nextInt(6) + 1) * 10;
                        gameItems.add(new Armor("Armor (" + power + ")", power));
                        break;
                    case WEAPON:
                        power = (r.nextInt(7) + 1) * 10;
                        gameItems.add(new Weapon("Weapon (" + power + ")", power));
                        break;
                    default:
                        break;
                }
            }
            return gameItems;
        }
        return null;
    }

    /**
     * Random Enemy generator. For every item category
     * generate a random item. Number of overall Items
     * is specified by the map dimensions.
     *
     * @param numberOfEnemies Number of Enemies
     * @return Returns the gameTests's monster list
     * @see ItemType class.
     */
    public static Collection<Enemy> generateEnemies(int numberOfEnemies) {
        if (numberOfEnemies > 0) {

            Collection<Enemy> enemies = new Stack<>();

            for (int i = 0; i < numberOfEnemies; i++) {
                if (i == 0) {
                    enemies.add(new Enemy(ENEMY_LEVEL_ONE_HEALTH, ENEMY_LEVEL_ONE_ATTACK_POWER, ENEMY_LEVEL_ONE_DEFENSE_POWER));
                } else if (i == 1) {
                    enemies.add(new Enemy(ENEMY_LEVEL_TWO_HEALTH, ENEMY_LEVEL_TWO_ATTACK_POWER, ENEMY_LEVEL_TWO_DEFENSE_POWER));
                } else if (i == 2) {
                    enemies.add(new Enemy(ENEMY_LEVEL_THREE_HEALTH, ENEMY_LEVEL_THREE_ATTACK_POWER, ENEMY_LEVEL_THREE_DEFENSE_POWER));
                }
            }
            return enemies;
        }
        return null;
    }
}
