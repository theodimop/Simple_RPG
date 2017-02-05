package Test02_InventoryTests.inventory_tests;

import static org.junit.Assert.*;
import org.junit.Test;

import inventory.*;

public class ArmorTest extends ItemTest {
	public static final int DEFENCE = 10;
	
	protected Item createTestSubject() {
		NAME = "Shield";
		return new Armor(NAME, DEFENCE);
	}
	
	@Test
	public void testGetDefensivePower() throws Exception {
		assertEquals(new Integer(DEFENCE),new Integer(((Armor)testSubject).getDefensivePower()));
	}
	
	@Test
	public void testEquipOn() throws Exception {
		PLAYER.pickup(testSubject);
		assertTrue(((Armor)testSubject).equipOn(PLAYER));
	}
	
	@Test
	public void testEquipOnGetEquipped() throws Exception {
		PLAYER.pickup(testSubject);
		((Armor)testSubject).equipOn(PLAYER);
		assertEquals(testSubject,PLAYER.getEquippedArmor());
	}
	
	@Test
	public void testEquipArmorNotInInventory() throws Exception {
		assertFalse(((Armor)testSubject).equipOn(PLAYER));
	}
}