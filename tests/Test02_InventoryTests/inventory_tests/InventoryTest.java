package Test02_InventoryTests.inventory_tests;

import static org.junit.Assert.*;
import org.junit.Test;

import inventory.*;

public class InventoryTest {
	private static final int CAPACITY = 3;
	protected static final Item POISON = new Poison("Small Poison", 1);
	protected static final Item POTION = new HealthPotion("Small Potion", 1);
	
	private Inventory createTestSubject() {
		return new Inventory(CAPACITY);
	}

	@Test
	public void testGetSize() throws Exception {
		Inventory testSubject = createTestSubject();
		assertEquals(new Integer(0), new Integer(testSubject.getSize()));
	}

	@Test
	public void testGetCapacity() throws Exception {
		Inventory testSubject = createTestSubject();
		assertEquals(new Integer(CAPACITY), new Integer(testSubject.getCapacity()));
	}
	
	@Test
	public void testAdd() throws Exception {
		Inventory testSubject = createTestSubject();
		assertTrue(testSubject.add(POISON));
	}
	
	@Test
	public void testAddContains() throws Exception {
		Inventory testSubject = createTestSubject();
		testSubject.add(POISON);
		assertTrue(testSubject.contains(POISON));
	}
	
	@Test
	public void testAddGetSize() throws Exception {
		Inventory testSubject = createTestSubject();
		testSubject.add(POISON);
		assertEquals(new Integer(1), new Integer(testSubject.getSize()));
	}
	
	@Test
	public void testAddGetCapacity() throws Exception {
		Inventory testSubject = createTestSubject();
		testSubject.add(POISON);
		assertEquals(new Integer(CAPACITY), new Integer(testSubject.getCapacity()));
	}
	
	@Test
	public void testAddTwice() throws Exception {
		Inventory testSubject = createTestSubject();
		testSubject.add(POISON);
		assertFalse(testSubject.add(POISON));
	}
	
	@Test
	public void testAddTwiceGetSize() throws Exception {
		Inventory testSubject = createTestSubject();
		testSubject.add(POISON);
		testSubject.add(POISON);
		assertEquals(new Integer(1), new Integer(testSubject.getSize()));
	}
	
	@Test
	public void testAddAll() throws Exception {
		Inventory testSubject = createTestSubject();
		Inventory inventory = createTestSubject();
		inventory.add(POISON);
		inventory.add(POTION);
		testSubject.addAll(inventory);
		assertTrue(testSubject.contains(POISON));
		assertTrue(testSubject.contains(POTION));
	}

	@Test
	public void testRemove() throws Exception {
		Inventory testSubject = createTestSubject();
		testSubject.add(POISON);
		assertTrue(testSubject.remove(POISON));
	}

	@Test
	public void testRemoveGetSize() throws Exception {
		Inventory testSubject = createTestSubject();
		testSubject.add(POISON);
		testSubject.remove(POISON);
		assertEquals(new Integer(0), new Integer(testSubject.getSize()));
	}
	
	@Test
	public void testRemoveGetCapacity() throws Exception {
		Inventory testSubject = createTestSubject();
		testSubject.add(POISON);
		testSubject.remove(POISON);
		assertEquals(new Integer(CAPACITY), new Integer(testSubject.getCapacity()));
	}

	@Test
	public void testRemoveContains() throws Exception {
		Inventory testSubject = createTestSubject();
		testSubject.add(POISON);
		testSubject.remove(POISON);
		assertFalse(testSubject.contains(POISON));
	}
	
	@Test
	public void testRemoveEmpty() throws Exception {
		Inventory testSubject = createTestSubject();
		assertFalse(testSubject.remove(POISON));
	}
	
	@Test
	public void testRemoveIndex() throws Exception {
		Inventory testSubject = createTestSubject();
		testSubject.add(POISON);
		assertTrue(testSubject.remove(0));
	}
	
	@Test
	public void testRemoveIndexGetSize() throws Exception {
		Inventory testSubject = createTestSubject();
		testSubject.add(POISON);
		testSubject.remove(0);
		assertEquals(new Integer(0), new Integer(testSubject.getSize()));
	}
	
	@Test
	public void testRemoveIndexGetCapacity() throws Exception {
		Inventory testSubject = createTestSubject();
		testSubject.add(POISON);
		testSubject.remove(0);
		assertEquals(new Integer(CAPACITY), new Integer(testSubject.getCapacity()));
	}

	@Test
	public void testRemoveIndexContains() throws Exception {
		Inventory testSubject = createTestSubject();
		testSubject.add(POISON);
		testSubject.remove(0);
		assertFalse(testSubject.contains(POISON));
	}

	@Test
	public void testRemoveIndexEmpty() throws Exception {
		Inventory testSubject = createTestSubject();
		assertFalse(testSubject.remove(1));
	}

	@Test
	public void testGet() throws Exception {
		Inventory testSubject = createTestSubject();
		testSubject.add(POISON);
		assertEquals(POISON, testSubject.get(0));
	}

	@Test
	public void testGetOutOfBounds() throws Exception {
		Inventory testSubject = createTestSubject();
		assertNull(testSubject.get(1));
	}
	
	@Test
	public void testContains() throws Exception {
		Inventory testSubject = createTestSubject();
		assertFalse(testSubject.contains(POISON));
	}

	@Test
	public void testIncreaseCapacity() throws Exception {
		Inventory testSubject = createTestSubject();
		testSubject.increaseCapacity(CAPACITY);
		assertEquals(new Integer(CAPACITY+CAPACITY), new Integer(testSubject.getCapacity()));
	}
}