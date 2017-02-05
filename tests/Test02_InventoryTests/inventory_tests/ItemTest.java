package Test02_InventoryTests.inventory_tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import actors.*;
import inventory.*;

public abstract class ItemTest {
	protected String NAME;
	protected Player PLAYER;
	protected Item testSubject;
	
	protected abstract Item createTestSubject();

	@Before
	public void setup() {
		PLAYER = new Player("Link",10,10,10,10);
		testSubject = createTestSubject();
	}

	@Test
	public void testGetName() throws Exception {
		assertEquals(NAME,testSubject.getName());
	}
}