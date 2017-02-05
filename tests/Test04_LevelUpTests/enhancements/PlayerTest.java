package Test04_LevelUpTests.enhancements;

import static org.junit.Assert.*;
import org.junit.Test;

import actors.Player;

public class PlayerTest {
	private static final String NAME = "Link";

	protected Player createTestSubject(int health, int attack, int defence, int capacity) {
		return new Player(NAME, health, attack, defence, capacity);
	}
	
	@Test
	public void testLevelUpAttackHigh1() {
		Player testSubject = createTestSubject(1,35,1,1);
		testSubject.levelUp();
		assertEquals(new Integer(39), new Integer(testSubject.getAttackPower()));
	}
	
	@Test
	public void testLevelUpAttackHigh2() {
		Player testSubject = createTestSubject(1,34,1,1);
		testSubject.levelUp();
		assertEquals(new Integer(37), new Integer(testSubject.getAttackPower()));
	}

	@Test
	public void testLevelUpAttackLow() {
		Player testSubject = createTestSubject(1,1,1,1);
		testSubject.levelUp();
		assertEquals(new Integer(2), new Integer(testSubject.getAttackPower()));
	}
	
	@Test
	public void testLevelUpDefenseHigh1() {
		Player testSubject = createTestSubject(1,1,35,1);
		testSubject.levelUp();
		assertEquals(new Integer(39), new Integer(testSubject.getDefensivePower()));
	}
	
	@Test
	public void testLevelUpDefenseHigh2() {
		Player testSubject = createTestSubject(1,1,34,1);
		testSubject.levelUp();
		assertEquals(new Integer(37), new Integer(testSubject.getDefensivePower()));
	}

	@Test
	public void testLevelUpDefenseLow() {
		Player testSubject = createTestSubject(1,1,1,1);
		testSubject.levelUp();
		assertEquals(new Integer(2), new Integer(testSubject.getDefensivePower()));
	}
	
	@Test
	public void testLevelCapacityHigh1() {
		Player testSubject = createTestSubject(1,1,1,35);
		testSubject.levelUp();
		assertEquals(new Integer(39), new Integer(testSubject.getInventory().getCapacity()));
	}
	
	@Test
	public void testLevelUpCapacityHigh2() {
		Player testSubject = createTestSubject(1,1,1,34);
		testSubject.levelUp();
		assertEquals(new Integer(37), new Integer(testSubject.getInventory().getCapacity()));
	}

	@Test
	public void testLevelUpCapacityLow() {
		Player testSubject = createTestSubject(1,1,1,1);
		testSubject.levelUp();
		assertEquals(new Integer(2), new Integer(testSubject.getInventory().getCapacity()));
	}
}