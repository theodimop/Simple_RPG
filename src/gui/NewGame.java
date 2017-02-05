package gui;

import actors.Actor;
import actors.Player;
import game.Game;
import game.GameMap;
import game.Room;
import inventory.Inventory;
import inventory.Item;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;


/**
 * ----------------------------------------------------------------------------  <br>
 * NewGame.java created byTheo Dimopoulos on 06-10-2016.                                <br>
 * Email:   dimopoulosth.td@gmail.com | td41@st-andrews.ac.uk                             <br>
 * ----------------------------------------------------------------------------  <br>
 * This class creates a new game, with playen name taked from
 * GUI MENU
 *
 * @author Theo Dimopoulos
 * @version 06-10-2016
 */
public class NewGame {

    private static final int PLAYER_INVENTORY_CAPACITY = 4;
    private static final int PLAYER_HEALTH = 100;
    private static final int PLAYER_ATTACK_POWER = 30;
    private static final int PLAYER_DEFENSE_POWER = 20;
    private static final int SEED = 33;


    private Game game;
    private GameMap map;     //Game Map
    private Player player;   //User player

    private Random r = new Random(SEED);
    private String playerName;
    private int mapSize;

    private int numberOfGameEnemies;    //Number of all enemies in game

    public NewGame(String playerName, int mapSize) {
        this.playerName = playerName;
        this.mapSize = mapSize;
        createGame();
    }

    public Game getGame() {
        return game;
    }

    public GameMap getMap() {
        return map;
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * Creates a new Game. Initializes the game Elements.
     */
    private void createGame() {
        player = new Player(playerName, PLAYER_HEALTH, PLAYER_ATTACK_POWER, PLAYER_DEFENSE_POWER, PLAYER_INVENTORY_CAPACITY);
        map = initializeMap();
        game = new Game(map, player);
    }

    /**
     * Initialize Map.
     * Add rooms with items and enemies to map.
     *
     * @return Returns the game Map.
     */
    private GameMap initializeMap() {
        GameMap map = new GameMap(mapSize, mapSize);
        Room room;

        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                if (i == 0 && j == 0) {
                    room = createStartingRoom();
                } else {
                    room = createRoomWithInventoryAndEnemy();
                }
                map.addRoom(room, i, j);
            }
        }
        return map;
    }

    /**
     * Create a Room with random items in its
     * inventory and enemy.
     *
     * @return Returns a map room.
     */
    private Room createRoomWithInventoryAndEnemy() {
        Inventory inventory = createInventoryWithRandomItems();
        int numberOfEnemiesInRoom = r.nextInt(3) + 1;
        numberOfGameEnemies += numberOfEnemiesInRoom;
        return new Room(inventory, GameElementsGenerator.generateEnemies(numberOfEnemiesInRoom));
    }

    /**
     * Create the Starting point Room, in which
     * player is located at first. This room hasn't
     * enemies.
     *
     * @return Returns the starting point room.
     */
    private Room createStartingRoom() {
        Inventory inventory = createInventoryWithRandomItems();
        Collection<Actor> startRoomOccupants = new HashSet<>();
        startRoomOccupants.add(player);

        return new Room(inventory, startRoomOccupants);
    }

    /**
     * Creates and inventory with random items inside.
     *
     * @return Returns an inventory with items.
     */
    private Inventory createInventoryWithRandomItems() {
        Inventory inventory = new Inventory();
        Collection<Item> items = GameElementsGenerator.generateItems(r.nextInt(3) + 1);
        if (items != null) {
            items.forEach(inventory::add);
        }
        return inventory;
    }

    /**
     * Getter for the number of game enemy actors.
     *
     * @return Returns the number of enemy actors
     */
    public int getNumberOfGameEnemies() {
        return numberOfGameEnemies;
    }

}
