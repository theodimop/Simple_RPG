package Test03_GameTests.game_tests;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import game.*;
import actors.*;
import inventory.*;

public class RoomTest {
	private Item ITEM;
	private Player PLAYER;
	private Room testSubject;

	public Room createTestSubject() {
		return new Room(new Inventory(), null);
	}
	
	public Room createTestSubjectWithInventory() {
		Inventory inv = new Inventory();
		inv.add(ITEM);
		return new Room(inv, null);
	}
	
	@Before
	public void setup() {
		ITEM = new Poison("Small Poison", 1);
		PLAYER = new Player("Link",10,10,10,10);
	}
	
	@Test
	public void testAddToInventory() throws Exception {
		testSubject = createTestSubject();
		assertTrue(testSubject.addToInventory(ITEM));
	}

	@Test
	public void testAddToInventoryContains() throws Exception {
		testSubject = createTestSubject();
		testSubject.addToInventory(ITEM);
		assertTrue(testSubject.inventoryContains(ITEM));
	}

	@Test
	public void testAddAlreadyInInventory() throws Exception {
		testSubject = createTestSubjectWithInventory();
		assertFalse(testSubject.addToInventory(ITEM));
	}

	@Test
	public void testAddToInventoryTwice() throws Exception {
		testSubject = createTestSubject();
		testSubject.addToInventory(ITEM);
		assertFalse(testSubject.addToInventory(ITEM));
	}

	@Test
	public void testRemoveFromInventory() throws Exception {
		testSubject = createTestSubjectWithInventory();
		assertTrue(testSubject.removeFromInventory(ITEM));
	}

	@Test
	public void testRemovedItemLeavesInventory() throws Exception {
		testSubject = createTestSubjectWithInventory();
		testSubject.removeFromInventory(ITEM);
		assertFalse(testSubject.inventoryContains(ITEM));
	}

	@Test
	public void testRemoveItemNotInRoom() throws Exception {
		testSubject = createTestSubject();
		assertFalse(testSubject.removeFromInventory(ITEM));
	}

	@Test
	public void testInventoryContains() throws Exception {
		testSubject = createTestSubjectWithInventory();
		assertTrue(testSubject.inventoryContains(ITEM));
	}

	@Test
	public void testInventoryNotContains() throws Exception {
		testSubject = createTestSubject();
		assertFalse(testSubject.inventoryContains(ITEM));
	}
	
	@Test
	public void testAddOccupant() throws Exception {
		testSubject = createTestSubject();
		assertTrue(testSubject.addOccupant(PLAYER));
	}

	@Test
	public void testAddOccupantContains() throws Exception {
		testSubject = createTestSubject();
		testSubject.addOccupant(PLAYER);
		assertTrue(testSubject.contains(PLAYER));
	}
	
	@Test
	public void testAddOccupantTwice() throws Exception {
		testSubject = createTestSubject();
		testSubject.addOccupant(PLAYER);
		assertFalse(testSubject.addOccupant(PLAYER));
	}
	
	@Test
	public void testRemoveOccupant() throws Exception {
		testSubject = createTestSubject();
		testSubject.addOccupant(PLAYER);
		assertTrue(testSubject.removeOccupant(PLAYER));
	}

	@Test
	public void testRemoveOccupantNotInRoom() throws Exception {
		testSubject = createTestSubject();
		assertFalse(testSubject.removeOccupant(PLAYER));
	}
	
	@Test
	public void testContains() throws Exception {
		testSubject = createTestSubject();
		assertFalse(testSubject.contains(PLAYER));
	}
}