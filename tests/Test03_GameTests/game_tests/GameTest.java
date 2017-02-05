package Test03_GameTests.game_tests;

import static org.junit.Assert.*;

import org.junit.Test;

import game.*;
import actors.*;
import inventory.*;

public class GameTest {
	private GameMap map;
	private Player player;
	private Enemy enemy;
	private Weapon weapon;
	private Armor armor;
	private HealthPotion potion;
	private Poison poison;
	
	// [PET] - Player, Enemy Treasure
	// Map : [--T][---]
	//       [P-T][XXX]
	//		 [XXX][XXX]
	private Game createTestSubject() {
		player = new Player("Link", 10, 10, 10, 10);
		weapon = new Weapon("Knife", 8);
		armor = new Armor("Shirt", 2);
		potion = new HealthPotion("Large", 8);
		poison = new Poison("Small", 5);
		player.pickup(poison);
		map = new GameMap(2, 3);
		map.generateRoom(0, 0);
		map.getRoom(0, 0).addToInventory(weapon);
		map.generateRoom(0, 1);
		map.getRoom(0, 1).addOccupant(player);
		map.getRoom(0, 1).addToInventory(potion);	
		map.generateRoom(1, 0);
		return new Game(map, player);
	}

	@Test
	public void testIsValidMove() {
		Game testSubject = createTestSubject();
		assertTrue(testSubject.isValidMove(player, map.getRoom(0, 0)));
	}

	@Test
	public void testNotIsValidMove() {
		Game testSubject = createTestSubject();
		assertFalse(testSubject.isValidMove(player, map.getRoom(1, 0)));
	}
	
	@Test
	public void testMoveToTrue() {
		Game testSubject = createTestSubject();
		assertTrue(testSubject.moveTo(player, map.getRoom(0, 0)));
	}

	@Test
	public void testMoveToRoom() {
		Game testSubject = createTestSubject();
		testSubject.moveTo(player, map.getRoom(0, 0));
		assertEquals(map.getRoom(0, 0), map.getRoomContaining(player));
	}
	
	@Test
	public void testMoveToFalse() {
		Game testSubject = createTestSubject();
		assertFalse(testSubject.moveTo(player, map.getRoom(1, 0)));
	}
	
	@Test
	public void testMoveToFalseRoom() {
		Game testSubject = createTestSubject();
		testSubject.moveTo(player, map.getRoom(1, 0));
		assertNotEquals(map.getRoom(1, 1), map.getRoomContaining(player));
	}
	
	@Test
	public void testMoveToFalseRoom2() {
		Game testSubject = createTestSubject();
		testSubject.moveTo(player, map.getRoom(1, 0));
		assertEquals(map.getRoom(0, 1), map.getRoomContaining(player));
	}
	
	@Test
	public void testPickupTrue() {
		Game testSubject = createTestSubject();
		assertTrue(testSubject.pickup(player, potion));
	}

	@Test
	public void testPickupFalse() throws Exception {
		Game testSubject = createTestSubject();
		assertFalse(testSubject.pickup(player, weapon));
	}
	
	@Test
	public void testDrop() throws Exception {
		Game testSubject = createTestSubject();
		assertTrue(testSubject.drop(player, poison));
	}

	@Test
	public void testDropRoomContains() throws Exception {
		Game testSubject = createTestSubject();
		testSubject.drop(player, poison);
		assertTrue(map.getRoomContaining(player).inventoryContains(poison));
	}

	@Test
	public void testDropRoomPlayerNotContains() throws Exception {
		Game testSubject = createTestSubject();
		testSubject.drop(player, poison);
		assertFalse(player.inventoryContains(poison));
	}

	@Test
	public void testUseConsumableOnSelf() {
		Game testSubject = createTestSubject();
		assertTrue(testSubject.useConsumableOn(player, poison, player));
	}
	
	@Test
	public void testUseConsumable() {
		Game testSubject = createTestSubject();
		enemy = new Enemy(10,10,10);
		map.getRoomContaining(player).addOccupant(enemy);
		assertTrue(testSubject.useConsumableOn(player, poison, enemy));
	}
	
	@Test
	public void testUseConsumableLeavesInventory() {
		Game testSubject = createTestSubject();
		enemy = new Enemy(10,10,10);
		map.getRoomContaining(player).addOccupant(enemy);
		testSubject.useConsumableOn(player, poison, enemy);
		assertFalse(player.inventoryContains(poison));
	}	

	@Test
	public void testUseConsumableLeavesInventory2() {
		Game testSubject = createTestSubject();
		enemy = new Enemy(10,10,10);
		map.getRoomContaining(player).addOccupant(enemy);
		testSubject.useConsumableOn(player, poison, enemy);
		assertFalse(map.getRoomContaining(player).inventoryContains(poison));
	}	

	@Test
	public void testUseConsumableNotInInventory() {
		Game testSubject = createTestSubject();
		enemy = new Enemy(10,10,10);
		map.getRoomContaining(player).addOccupant(enemy);
		assertFalse(testSubject.useConsumableOn(player, potion, enemy));
	}
	
	@Test
	public void testUseConsumableOtherRoom() {
		Game testSubject = createTestSubject();
		enemy = new Enemy(10,10,10);
		map.getRoom(0,0).addOccupant(enemy);
		assertFalse(testSubject.useConsumableOn(player, poison, enemy));
	}
	
	@Test
	public void testUseConsumableOtherRoomRemainsInInventory() {
		Game testSubject = createTestSubject();
		enemy = new Enemy(10,10,10);
		map.getRoom(0,0).addOccupant(enemy);
		testSubject.useConsumableOn(player, poison, enemy);
		assertTrue(player.inventoryContains(poison));
	}
	
	@Test
	public void TestEquipOnTrue() {
		Game testSubject = createTestSubject();
		player.pickup(armor);
		assertTrue(testSubject.equipOn(player,armor));
	}

	@Test
	public void TestEquipOnFalse() {
		Game testSubject = createTestSubject();
		assertFalse(testSubject.equipOn(player,armor));
	}
	
	@Test
	public void testFightTrue1() throws Exception {
		Game testSubject = createTestSubject();
		enemy = new Enemy(20,10,10);
		map.getRoomContaining(player).addOccupant(enemy);
		assertTrue(testSubject.fight(player,enemy));
	}

	@Test
	public void testFightTrue2() throws Exception {
		Game testSubject = createTestSubject();
		enemy = new Enemy(5,10,10);
		map.getRoomContaining(player).addOccupant(enemy);
		assertTrue(testSubject.fight(player,enemy));
	}
	
	@Test
	public void testFightDefeat() throws Exception {
		Game testSubject = createTestSubject();
		enemy = new Enemy(5,10,10);
		enemy.pickup(armor);
		map.getRoomContaining(player).addOccupant(enemy);
		testSubject.fight(player,enemy);
		assertEquals(new Integer(0), new Integer(enemy.getInventory().getSize()));
	}
	
	@Test
	public void testFightDefeat2() throws Exception {
		Game testSubject = createTestSubject();
		enemy = new Enemy(5,10,10);
		enemy.pickup(armor);
		map.getRoomContaining(player).addOccupant(enemy);
		testSubject.fight(player,enemy);
		assertTrue(map.getRoomContaining(player).inventoryContains(armor));
	}
	
	@Test
	public void testFightFalse() throws Exception {
		Game testSubject = createTestSubject();
		enemy = new Enemy(5,10,10);
		map.getRoom(0,0).addOccupant(enemy);
		assertFalse(testSubject.fight(player,enemy));
	}
}