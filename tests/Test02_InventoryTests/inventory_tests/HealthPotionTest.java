package Test02_InventoryTests.inventory_tests;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import actors.*;
import inventory.*;

public class HealthPotionTest {
	public static final String NAME = "Large Potion";
	public static final int HEAL = 20;
	private Player PLAYER;
	private HealthPotion testSubject;
	
	private HealthPotion createTestSubject() {
		return new HealthPotion(NAME, HEAL);
	}

	@Before
	public void setup() {
		PLAYER = new Player("Link",10,10,10,10);
		testSubject = createTestSubject();
	}
	
	@Test
	public void testGetHealingPower() throws Exception {
		assertEquals(new Integer(HEAL),new Integer(testSubject.getHealingPower()));
	}
	
	@Test
	public void testUseOnGetHealth() throws Exception {
		PLAYER.pickup(testSubject);
		testSubject.useOn(PLAYER);
		assertEquals(new Integer(HEAL+10), new Integer(PLAYER.getHealth()));
	}
}