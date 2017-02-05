package Test01_ActorTests.actor_tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import actors.Actor;
import actors.Player;
import inventory.Armor;
import inventory.Weapon;

public class PlayerTest extends ActorTest {
	private static final String NAME = "Link";
	private static final int CAPACITY = 2;
	private Weapon WEAPON;
	private Weapon WEAPON2;
	private Armor ARMOR;
	private Armor ARMOR2;
	
	protected Actor createTestSubject() {
		return new Player(NAME, HEALTH, ATTACK, DEFENCE, CAPACITY);
	}

	protected Actor createTestSubject(int health, int attack, int defence) {
		return new Player(NAME, health, attack, defence, CAPACITY);
	}
	
	protected Actor createTestSubjectWithInventory() {
		Player p = new Player(NAME, HEALTH, ATTACK, DEFENCE, CAPACITY);
		p.pickup(ITEM);
		return p;
	}

	@Before
	public void setup() {
		WEAPON = new Weapon("Sword", 2);
		WEAPON2 = new Weapon("Sword", 2);
		ARMOR = new Armor("Shield", 3);
		ARMOR2 = new Armor("Armor", 4);		
	}
	
	@Test
	public void testGetName() {
		Player testSubject = (Player)createTestSubject();
		assertEquals(NAME,testSubject.getName());
	}

	@Test
	public void testSetName() {
		Player testSubject = (Player)createTestSubject();
		testSubject.setName(NAME+NAME);
		assertEquals(NAME+NAME,testSubject.getName());
	}

	@Test
	public void testEquipArmor() {
		Player testSubject = (Player)createTestSubject();
		testSubject.pickup(ARMOR);
		assertTrue(testSubject.equip(ARMOR));
	}

	@Test
	public void testEquipArmorNotInInventory() {
		Player testSubject = (Player)createTestSubject();
		assertFalse(testSubject.equip(ARMOR));
	}

	@Test
	public void testGetEquippedArmor() {
		Player testSubject = (Player)createTestSubject();
		testSubject.pickup(ARMOR);
		testSubject.equip(ARMOR);
		assertEquals(ARMOR,testSubject.getEquippedArmor());
	}

	@Test
	public void testGetEquippedArmorNull() {
		Player testSubject = (Player)createTestSubject();
		assertNull(testSubject.getEquippedArmor());
	}

	@Test
	public void testUnequipArmor() {
		Player testSubject = (Player)createTestSubject();
		testSubject.pickup(ARMOR);
		testSubject.equip(ARMOR);
		testSubject.unequipArmor();
		assertNull(testSubject.getEquippedArmor());
	}

	@Test
	public void testUnequipArmorRemainsInInventory() {
		Player testSubject = (Player)createTestSubject();
		testSubject.pickup(ARMOR);
		testSubject.equip(ARMOR);
		testSubject.unequipArmor();
		assertTrue(testSubject.inventoryContains(ARMOR));
	}

	@Test
	public void testUnequipNullArmor() {
		Player testSubject = (Player)createTestSubject();
		testSubject.unequipArmor();
		assertNull(testSubject.getEquippedArmor());
	}

	@Test
	public void testReplaceEquippedArmor() {
		Player testSubject = (Player)createTestSubject();
		testSubject.pickup(ARMOR);
		testSubject.pickup(ARMOR2);
		testSubject.equip(ARMOR);
		testSubject.equip(ARMOR2);
		assertEquals(ARMOR2,testSubject.getEquippedArmor());
	}
	
	@Test
	public void testGetDefencePowerWithArmor() {
		Player testSubject = (Player)createTestSubject();
		testSubject.pickup(ARMOR);
		testSubject.equip(ARMOR);
		assertEquals(new Integer(DEFENCE + ARMOR.getDefensivePower()), new Integer(testSubject.getDefensivePower()));
	}
	
	@Test
	public void testEquipWeapon() {
		Player testSubject = (Player)createTestSubject();
		testSubject.pickup(WEAPON);
		assertTrue(testSubject.equip(WEAPON));
	}

	@Test
	public void testEquipWeaponNotInInventory() {
		Player testSubject = (Player)createTestSubject();
		assertFalse(testSubject.equip(WEAPON));
	}

	@Test
	public void testGetEquippedWeapon() {
		Player testSubject = (Player)createTestSubject();
		testSubject.pickup(WEAPON);
		testSubject.equip(WEAPON);
		assertEquals(WEAPON,testSubject.getEquippedWeapon());
	}

	@Test
	public void testGetEquippedWeaponNull() {
		Player testSubject = (Player)createTestSubject();
		assertNull(testSubject.getEquippedWeapon());
	}

	@Test
	public void testUnequipWeapon() {
		Player testSubject = (Player)createTestSubject();
		testSubject.pickup(WEAPON);
		testSubject.equip(WEAPON);
		testSubject.unequipWeapon();
		assertNull(testSubject.getEquippedWeapon());
	}

	@Test
	public void testUnequipWeaponRemainsInInventory() {
		Player testSubject = (Player)createTestSubject();
		testSubject.pickup(WEAPON);
		testSubject.equip(WEAPON);
		testSubject.unequipWeapon();
		assertTrue(testSubject.inventoryContains(WEAPON));
	}

	@Test
	public void testUnequipNullWeapon() {
		Player testSubject = (Player)createTestSubject();
		testSubject.unequipWeapon();
		assertNull(testSubject.getEquippedWeapon());
	}
	
	@Test
	public void testGetAttackPowerWithWeapon() {
		Player testSubject = (Player)createTestSubject();
		testSubject.pickup(WEAPON);
		testSubject.equip(WEAPON);
		assertEquals(new Integer(ATTACK + WEAPON.getAttackPower()), new Integer(testSubject.getAttackPower()));
	}

	@Test
	public void testReplaceEquipedWeapon() {
		Player testSubject = (Player)createTestSubject();
		testSubject.pickup(WEAPON);
		testSubject.pickup(WEAPON2);
		testSubject.equip(WEAPON);
		testSubject.equip(WEAPON2);
		assertEquals(WEAPON2,testSubject.getEquippedWeapon());
	}
	
	@Test
	public void testPickupExceedsCapacity() {
		Actor testSubject = createTestSubject();
		testSubject.pickup(ITEM);
		testSubject.pickup(WEAPON);
		assertFalse(testSubject.pickup(ARMOR));
	}

	@Test
	public void testDropEquippedArmor() {
		Player testSubject = (Player)createTestSubject();
		testSubject.pickup(ARMOR);
	    testSubject.equip(ARMOR);
        testSubject.drop(ARMOR);
		assertNull(testSubject.getEquippedArmor());
	}
	
	@Test
	public void testDropEquippedWeapon() {
		Player testSubject = (Player)createTestSubject();
		testSubject.pickup(WEAPON);
		testSubject.equip(WEAPON);
		testSubject.drop(WEAPON);
		assertNull(testSubject.getEquippedWeapon());
	}
}