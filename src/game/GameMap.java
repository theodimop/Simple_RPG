package game;

import actors.Actor;

/**
 * ----------------------------------------------------------------------------  <br>
 * gameTests.GameMap.java created byTheo Dimopoulos on 19-09-2016.                                <br>
 * Email:   dimopoulosth.td@gmail.com | td41@st-andrews.ac.uk                             <br>
 * ----------------------------------------------------------------------------  <br>
 * game.GameMap.java is a 2D map which include rooms.
 *
 * @author Theo Dimopoulos
 * @version 19-09-2016
 */
public class GameMap {

    private Room[][] mapRooms;       //Map 2D array
    private int width, height;       //Map dimensions

    /**
     * GameMap construction.
     *
     * @param width  Number of rooms in a single row
     * @param height Number of room in a single column
     */
    public GameMap(int width, int height) {
        this.width = width;
        this.height = height;
        mapRooms = initializeGameMap(width, height);      //Initialize the gameMap
    }

    /**
     * Generate room to a specific position in gameMap.
     *
     * @param x The position of the Room on the X extent of map
     * @param y The position of the Room on Y extent of map
     * @return Returns an new gameTests.Room object
     */
    public Room generateRoom(int x, int y) {
        if (validCoordinates(x, y)) {
            if (getRoom(x, y) == null) {
                mapRooms[x][y] = new Room();
            }
            return getRoom(x, y);
        } else {
            return null;
        }
    }

    /**
     * Adds the given room to the map.
     *
     * @param room The candidate for insertion room.
     * @param x    Position in column.
     * @param y    Position in row.
     * @return Returns true if room added successfully and false if false.
     */
    public boolean addRoom(Room room, int x, int y) {
        if (room != null) {
            if (validCoordinates(x, y)) {
                if (mapRooms[x][y] == null) {
                    mapRooms[x][y] = room;
                    return true;        //Room added successfully
                } else {
                    return false;       //Another rooms in that cell, return false
                }
            } else {
                return false;         //Coordinates out of map bounds
            }
        }
        return false;              //Invalid Params
    }

    /**
     * Getter for a room from the gameTests map.
     *
     * @param x Position in column.
     * @param y Position in row
     * @return Returns the room in the given coordinates. If
     * cell is empty it will return NULL!
     */
    public Room getRoom(int x, int y) {
        if (validCoordinates(x, y)) {
            return mapRooms[x][y];
        }
        return null;
    }

    /**
     * Searches the room that contains the given actors.Actor.
     *
     * @param character The actor, which constitutes key for the search.
     * @return Return the room that hosts the actor, null if actor isn't
     * on the gameTests Map.
     */
    public Room getRoomContaining(Actor character) {
        Room tempRoom;
        if (character != null) {

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    tempRoom = getRoom(i, j);
                    if (tempRoom != null && tempRoom.contains(character)) {
                        return tempRoom;
                    }
                }
            }
            return null;       //actorsTests.Actor not in gameTests map
        }
        return null;
    }

    /**
     * Check if the rooms given have manhattan distance 1.
     *
     * @param room1 The first room to check distance.
     * @param room2 The second room to check distance.
     * @return Returns true if player can move from the one to another
     * with a sing move.
     */
    public boolean isNextTo(Room room1, Room room2) {
        int x1 = -1, y1 = -1, x2 = -1, y2 = -1;


        if (room1 != null && room2 != null) {

            for (int i = 0; i < width; i++) {                                    //Find rooms coordinates
                for (int j = 0; j < height; j++) {
                    if (getRoom(i, j) != null) {
                        if (getRoom(i, j).equals(room1)) {
                            x1 = i;
                            y1 = j;
                        } else if (getRoom(i, j).equals(room2)) {
                            x2 = i;
                            y2 = j;
                        } else {
                            if (validCoordinates(x1, y1)
                                    && validCoordinates(x2, y2)) {      //Break the loop if Cords found.
                                break;
                            }
                        }
                    }
                }
            }
            return (Math.abs(x2 - x1) + Math.abs(y2 - y1) == 1);    //return true if are next
        }
        return false;

    }

    /**
     * Checks if room is already assigned to a map cell.
     *
     * @param room The search's key.
     * @return True if room is already on the map and false if not.
     */
    public boolean contains(Room room) {
        if (room != null) {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    if (mapRooms[i][j] != null && mapRooms[i][j].equals(room)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //My Enhancement Methods, not in UML

    /**
     * Implements a validation test to the coordinates
     * based on the map dimensions.
     *
     * @param x Coordinate x for row.
     * @param y Coordinate y for Column.
     * @return Returns Returns True if coordinates are valid.
     */
    private boolean validCoordinates(int x, int y) {
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    /**
     * Initialize the gameMap by generating rooms to
     * all the map cells.
     *
     * @param width  Map width.
     * @param height Map height.
     * @return Returns the initialized gameTests map.
     */
    private Room[][] initializeGameMap(int width, int height) {
        Room[][] tempGameMap = new Room[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tempGameMap[i][j] = null;
            }
        }
        return tempGameMap;
    }
}


