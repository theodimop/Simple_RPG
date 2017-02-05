package game;

import actors.Actor;
import actors.Enemy;
import actors.Player;
import inventory.Item;
import inventory.Equipment;
import inventory.Inventory;
import inventory.Armor;
import inventory.Weapon;
import inventory.Consumable;

import java.util.ArrayList;
import java.util.Collection;

/**
 * ----------------------------------------------------------------------------  <br>
 * game.Game.java created byTheo Dimopoulos on 19-09-2016.                                <br>
 * Email:   dimopoulosth.td@gmail.com | td41@st-andrews.ac.uk                             <br>
 * ----------------------------------------------------------------------------  <br>
 * game.Game.java represents a computer RPG game. The game has a 2D board
 * (implemented as gameMap class), players, variety of items and monster
 * characters.
 *
 * @author Theo Dimopoulos
 * @version 19-09-2016
 */
public class Game {

    private GameMap map;              //Game Map
    private Player player;            //User player
    private Collection enemies;       //Game enemies
    private Collection items;         //Game items

    // Overloaded Constructors

    /**
     * Constructor with map dimensions as input.
     *
     * @param mapHeight Number of map rows
     * @param mapWidth  Number of map columns
     * @param player    Game player (user)
     * @param enemies   Game enemy collection
     * @param items     Game items
     */
    public Game(int mapWidth, int mapHeight, Player player,
                Collection<Enemy> enemies,
                Collection<Item> items) {

        this.map = new GameMap(mapWidth, mapHeight);
        this.player = player;
        this.enemies = enemies;
        this.items = items;
    }

    /**
     * Constructor with Game Map given as argument.
     *
     * @param map    Game map.
     * @param player Game player (user)
     */
    public Game(GameMap map, Player player) {
        this(0, 0, player, new ArrayList<>(), new ArrayList<>());
        this.map = map;
    }

    /**
     * Checks if an actor can reach the given room.
     *
     * @param user Main player
     * @param room The requested for movement room
     * @return Returns true if move can take place and false if not
     */
    public boolean isValidMove(Actor user, Room room) {
        Room userRoom;
        if (user != null && room != null) {
            userRoom = map.getRoomContaining(user);
            return map.isNextTo(userRoom, room);
        }
        return false;
    }

    /**
     * Implements the movement to another room, if
     * room distance is one.
     *
     * @param actor The moving candidate actor.
     * @param room  The destination's room.
     * @return Returns true if actor was moved to the
     * room.
     */
    public boolean moveTo(Actor actor, Room room) {
        Room actorRoom;

        if (actor != null && room != null) {
            if (isValidMove(actor, room)) {
                actorRoom = map.getRoomContaining(actor);
                return actorRoom.removeOccupant(actor)
                        && room.addOccupant(actor);

            } else {
                return false;           //Invalid move
            }
        }
        return false;                   //null params
    }

    /**
     * Implements the pick up of an inventory.Item by an actor.
     *
     * @param actor Requested movind actor.
     * @param item  The picked up item.
     * @return Returns true if actor picked up
     * the item.
     */
    public boolean pickup(Actor actor, Item item) {
        Room actorRoom;

        if (actor != null && item != null) {
            actorRoom = map.getRoomContaining(actor);
            if (actor.pickup(item)) {
                return actorRoom.removeFromInventory(item);
            }
            return false;
        }
        return false;
    }

    /**
     * Implements the action of an actor, using a consumable
     * object on another actor or himself.
     *
     * @param user       Actor that will use the consumable
     * @param consumable The destination's room.
     * @param actor      The consuming candidate actor.
     * @return Returns true if actor used the consumable.
     */
    public boolean useConsumableOn(Actor user, Consumable consumable, Actor actor) {
        Room userRoom;
        if (user != null && consumable != null
                && actor != null) {

            userRoom = map.getRoomContaining(user);

            if (userRoom != null) {
                if (userRoom.contains(actor)) {
                    if (user.getInventory().remove(consumable)) {   //Remove consumable from inventory
                        consumable.useOn(actor);
                        return true;            //Consumable used successfully
                    } else {
                        return false;           //Consumable not in user's inventory
                    }
                } else {
                    return false;               //Actors not in same room
                }
            } else {
                return false;                   //User not in any room
            }
        }
        return false;                           // Null params given
    }

    /**
     * Implements the usage of an equipment that is
     * contained in users inventory.
     *
     * @param user      The candidate actor for using the asset
     * @param equipment The requested for movement room
     * @return Returns true if user was equipped
     */
    public boolean equipOn(Actor user, Equipment equipment) {
        Player player;

        if (user != null && equipment != null) {
            if (user instanceof Player) {
                player = (Player) user;

                if (player.inventoryContains(equipment)) {
                    if (equipment instanceof Armor) {
                        return player.equip((Armor) equipment);    //User equipped armor
                    } else if (equipment instanceof Weapon) {
                        return player.equip((Weapon) equipment);   //User equipped weapon
                    }
                } else {
                    return false;                           //inventory.Equipment not in inventory
                }
            } else {
                return false;                               //use not an instance of actors.Player
            }
        }
        return false;               //Null params given
    }

    /**
     * Implements the removal off of an item that was consumed
     * or left on purpose from user's inventory. If
     *
     * @param actor The candidate actor for dropping the item
     * @param item  The requested for dropping item
     * @return Returns true if item was dropped from inventory.
     */
    public boolean drop(Actor actor, Item item) {
        if (actor != null && item != null) {
            if (actor.inventoryContains(item)) {
                actor.drop(item);                           //Actor drops item
                map.getRoomContaining(actor).addToInventory(item);  //The item is added to Room
                return true;
            } else {
                return false;           //inventory.Item not in inventory
            }
        }
        return false;                   //Null params given
    }

    /**
     * Method fight represent a hit from an attacker, to
     * a defender actor. This method delegates to method
     * attack, which delegates to method defendAgainst.
     *
     * @param attacker The attacking actor.
     * @param defender The defending actor.
     * @return Returns true if the attacker killed the defender.
     */
    public boolean fight(Actor attacker, Actor defender) {
        Room battleRoom;
        int defenderLifeBeforeFight;
        if (attacker != null & defender != null) {
            battleRoom = map.getRoomContaining(attacker);
            defenderLifeBeforeFight = defender.getHealth();

            if (battleRoom.equals(map.getRoomContaining(defender))) {

                if (attacker instanceof Enemy && defender instanceof Player) {   //Ensure thet equipment is used to defense power
                    attacker.attack(defender);
                } else {
                    defender.defendAgainst(attacker);
                }

                if (!defender.isAlive()) {
                    battleRoom.addToInventory(defender.getInventory()); //Add inventory to battle room
                    battleRoom.removeOccupant(defender);                //Remove dead occupant
                    emptyActorsInventory(defender);                     //Empty defeated actor's inventory
                    return true;                                        //Return true because defender was killed
                } else {
                    return defenderLifeBeforeFight > defender.getHealth(); //Return if defender was hurt
                }
            }
            return false;   //Attacker and Defender are not in same room
        }
        return false;       //Null Params
    }

    //My Enhancement method, not in practicals UML

    /**
     * Implements the drop of inventory items of
     * an defeated actor.
     *
     * @param defeatedActor The deafeated actor.
     */
    private void emptyActorsInventory(Actor defeatedActor) {
        Inventory inventory;
        if (defeatedActor != null) {
            inventory = defeatedActor.getInventory();
            while (inventory.getSize() > 0) {
                inventory.remove(0);
            }
        }
    }
}
