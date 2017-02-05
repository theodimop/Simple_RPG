package Test03_GameTests.game_tests;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import actors.*;
import game.*;
import inventory.*;

public class GameMapTest {
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
		GameMap map = new GameMap(WIDTH,HEIGHT);
		return map;
	}
	
	public GameMap createTestSubject() {
		ENEMY_WEAK.pickup(WEAPON);
		ENEMY_STRONG.pickup(ARMOR);
		GameMap map = new GameMap(WIDTH,HEIGHT);
		map.generateRoom(0, 0);
		map.generateRoom(0, 1).addToInventory(ITEM);
		map.generateRoom(X1, Y1).addOccupant(ENEMY_WEAK);
		map.generateRoom(X1, Y1+1).addOccupant(ENEMY_STRONG);
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
	public void testGenerateRoom() {
		GameMap testSubject = createEmptyTestSubject();
		Room room = testSubject.generateRoom(X1, Y1);
		assertNotNull(room);
	}

	@Test
	public void testGenerateDuplicateRoom() {
		GameMap testSubject = createEmptyTestSubject();
		Room room = testSubject.generateRoom(X1, Y1);
		Room room2 = testSubject.generateRoom(X1, Y1);
		assertEquals(room, room2);
	}

	@Test
	public void testAddRoom() {
		GameMap testSubject = createEmptyTestSubject();
		Room room = new Room();
		assertTrue(testSubject.addRoom(room, X1, Y1));
	}

	@Test
	public void testAddRoomOutOfBoundsX1() {
		GameMap testSubject = createEmptyTestSubject();
		Room room = new Room();
		assertFalse(testSubject.addRoom(room, -1, Y1+1));
	}
	
	@Test
	public void testAddRoomOutOfBoundsX2() {
		GameMap testSubject = createEmptyTestSubject();
		Room room = new Room();
		assertFalse(testSubject.addRoom(room, WIDTH+1, Y1+1));
	}
	
	@Test
	public void testAddRoomOutOfBoundsY1() {
		GameMap testSubject = createEmptyTestSubject();
		Room room = new Room();
		assertFalse(testSubject.addRoom(room, X1, -1));
	}
	
	@Test
	public void testAddRoomOutOfBoundsY2() {
		GameMap testSubject = createEmptyTestSubject();
		Room room = new Room();
		assertFalse(testSubject.addRoom(room, X1, HEIGHT+1));
	}

	@Test
	public void testAddRoomEdgeX() {
		GameMap testSubject = createEmptyTestSubject();
		Room room = new Room();
		assertTrue(testSubject.addRoom(room, WIDTH-1, Y1));
	}	

	@Test
	public void testAddRoomEdgeY() {
		GameMap testSubject = createEmptyTestSubject();
		Room room = new Room();
		assertTrue(testSubject.addRoom(room, X1, HEIGHT-1));
	}

	@Test
	public void testGetNullRoom() {
		GameMap testSubject = createEmptyTestSubject();
		assertNull(testSubject.getRoom(X1, Y1));
	}

	@Test
	public void testGetRoom() {
		GameMap testSubject = createEmptyTestSubject();
		Room room = new Room();
		testSubject.addRoom(room, X1, Y1);
		assertEquals(room, testSubject.getRoom(X1, Y1));
	}

	@Test
	public void testGetRoomContaining() {
		GameMap testSubject = createEmptyTestSubject();
		Room room = new Room();
		testSubject.addRoom(room, X1, Y1);
		room.addOccupant(PLAYER);
		assertEquals(room, testSubject.getRoomContaining(PLAYER));
	}

	@Test
	public void testIsNextToX() {
		GameMap testSubject = createEmptyTestSubject();
		Room room1 = new Room();
		Room room2 = new Room();
		testSubject.addRoom(room1, X1, Y1);
		testSubject.addRoom(room2, X1-1, Y1);
		assertTrue(testSubject.isNextTo(room1, room2));
	}

	@Test
	public void testIsNextToY() {
		GameMap testSubject = createEmptyTestSubject();
		Room room1 = new Room();
		Room room2 = new Room();
		testSubject.addRoom(room1, X1, Y1);
		testSubject.addRoom(room2, X1, Y1+1);
		assertTrue(testSubject.isNextTo(room1, room2));
	}

	@Test
	public void testIsNextToDiagonalLR() {
		GameMap testSubject = createEmptyTestSubject();
		Room room1 = new Room();
		Room room2 = new Room();
		testSubject.addRoom(room1, X1, Y1);
		testSubject.addRoom(room2, X1+1, Y1+1);
		assertFalse(testSubject.isNextTo(room1, room2));
	}

	@Test
	public void testIsNextToDiagonalRL() {
		GameMap testSubject = createEmptyTestSubject();
		Room room1 = new Room();
		Room room2 = new Room();
		testSubject.addRoom(room1, X1+1, Y1);
		testSubject.addRoom(room2, X1, Y1+1);
		assertFalse(testSubject.isNextTo(room1, room2));
	}

	@Test
	public void testContains() {
		GameMap testSubject = createEmptyTestSubject();
		Room room = new Room();
		testSubject.addRoom(room, X1, Y1);
		assertTrue(testSubject.contains(room));
	}

	@Test
	public void testNotContains() {
		GameMap testSubject = createEmptyTestSubject();
		Room room = new Room();
		assertFalse(testSubject.contains(room));
	}
}