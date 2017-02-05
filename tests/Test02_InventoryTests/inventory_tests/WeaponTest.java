package Test02_InventoryTests.inventory_tests;

import static org.junit.Assert.*;
import org.junit.Test;

import inventory.*;

public class WeaponTest extends ItemTest {
	public static final int ATTACK = 10;
	
	@Override
	protected Item createTestSubject() {
		NAME = "Sword";
		return new Weapon(NAME, ATTACK);
	}
	
	@Test
	public void testGetAttackPower() throws Exception {
		assertEquals(new Integer(ATTACK),new Integer(((Weapon)testSubject).getAttackPower()));
	}
	
	@Test
	public void testEquipOn() throws Exception {
		PLAYER.pickup(testSubject);
		assertTrue(((Weapon)testSubject).equipOn(PLAYER));
	}
	
	@Test
	public void testEquipOnGetEquipped() throws Exception {
		PLAYER.pickup(testSubject);
		((Weapon)testSubject).equipOn(PLAYER);
		assertEquals(testSubject,PLAYER.getEquippedWeapon());
	}
	
	@Test
	public void testEquipWeaponNotInInventory() throws Exception {
		assertFalse(((Weapon)testSubject).equipOn(PLAYER));
	}
}