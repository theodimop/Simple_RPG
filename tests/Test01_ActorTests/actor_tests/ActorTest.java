package Test01_ActorTests.actor_tests;

import static org.junit.Assert.*;

import org.junit.Test;

import actors.Actor;
import inventory.Item;
import inventory.Poison;

public abstract class ActorTest {
	protected static final int HEALTH = 10;
	protected static final int ATTACK = 2;
	protected static final int DEFENCE = 3;
	protected static final Item ITEM = new Poison("Small Poison", 1);
	
	protected abstract Actor createTestSubject();

	protected abstract Actor createTestSubject(int health, int attack, int defence);
	
	protected abstract Actor createTestSubjectWithInventory();
	
	@Test
	public void testGetHealth() {
		Actor testSubject = createTestSubject();
		assertEquals(new Integer(HEALTH), new Integer(testSubject.getHealth()));
	}

	@Test
	public void testIsAlive() {
		Actor testSubject = createTestSubject();
		assertTrue(testSubject.isAlive());
	}

	@Test
	public void testNotIsAlive() {
		Actor testSubject = createTestSubject(-HEALTH, ATTACK, DEFENCE);
		assertFalse(testSubject.isAlive());
	}

	@Test
	public void testHeal() {
		Actor testSubject = createTestSubject();
		testSubject.heal(HEALTH);
		assertEquals(new Integer(HEALTH+HEALTH), new Integer(testSubject.getHealth()));
	}

	@Test
	public void testHitSuccess() {
		Actor testSubject = createTestSubject();
		Actor attacker = createTestSubject(HEALTH, DEFENCE*2, DEFENCE);
		testSubject.defendAgainst(attacker);
		//System.out.println (testSubject.getDefensivePower()+" Attacker: "+ attacker.getAttackPower());
		assertEquals(new Integer(HEALTH - DEFENCE*2), new Integer(testSubject.getHealth()));
	}

	@Test
	public void testHitFail() {
		Actor testSubject = createTestSubject();
		Actor attacker = createTestSubject(HEALTH, 1, DEFENCE);
		testSubject.defendAgainst(attacker);
		assertEquals(new Integer(HEALTH), new Integer(testSubject.getHealth()));
	}

	@Test
	public void testGetAttack() {
		Actor testSubject = createTestSubject();
		assertEquals(new Integer(ATTACK), new Integer(testSubject.getAttackPower()));
	}

	@Test
	public void testGetDefence() {
		Actor testSubject = createTestSubject();
		assertEquals(new Integer(DEFENCE), new Integer(testSubject.getDefensivePower()));
	}

	@Test
	public void testGetInventory() {
		Actor testSubject = createTestSubject();
		assertNotNull(testSubject.getInventory());
	}

	@Test
	public void testPickup() {
		Actor testSubject = createTestSubject();
		assertTrue(testSubject.pickup(ITEM));
	}

	@Test
	public void testPickupIsInInventory() {
		Actor testSubject = createTestSubject();
		testSubject.pickup(ITEM);
		assertTrue(testSubject.inventoryContains(ITEM));
	}
	
	@Test
	public void testPickupTwice() {
		Actor testSubject = createTestSubject();
		testSubject.pickup(ITEM);
		assertFalse(testSubject.pickup(ITEM));
	}

	@Test
	public void testDrop() {
		Actor testSubject = createTestSubjectWithInventory();
		assertTrue(testSubject.drop(ITEM));
	}

	@Test
	public void testDropItemLeavesInventory() {
		Actor testSubject = createTestSubjectWithInventory();
		testSubject.drop(ITEM);
		assertFalse(testSubject.inventoryContains(ITEM));
	}

	@Test
	public void testInventoryContains() {
		Actor testSubject = createTestSubjectWithInventory();
		assertTrue(testSubject.inventoryContains(ITEM));
	}

}