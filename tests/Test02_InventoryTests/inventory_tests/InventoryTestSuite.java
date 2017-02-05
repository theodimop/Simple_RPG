package Test02_InventoryTests.inventory_tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(

{ InventoryTest.class, HealthPotionTest.class, PoisonTest.class, 
		WeaponTest.class, ArmorTest.class })
public class InventoryTestSuite { // nothing
}
