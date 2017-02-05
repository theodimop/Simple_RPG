package Test02_InventoryTests.inventory_tests;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import actors.*;
import inventory.*;

public class PoisonTest {
	public static final String NAME = "Small Poison";
	public static final int POISON = 5;
	private Player PLAYER;
	private Poison testSubject;
	
	private Poison createTestSubject() {
		return new Poison(NAME, POISON);
	}

	@Before
	public void setup() {
		PLAYER = new Player("Link",10,10,10,10);
		testSubject = createTestSubject();
	}
	
	@Test
	public void testGetPoisonPower() throws Exception {
		assertEquals(new Integer(POISON),new Integer(testSubject.getPoisonPower()));
	}
	
	@Test
	public void testUseOnGetHealth() throws Exception {
		PLAYER.pickup(testSubject);
		testSubject.useOn(PLAYER);
		assertEquals(new Integer(10-POISON), new Integer(PLAYER.getHealth()));
	}
}