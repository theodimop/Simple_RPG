package Test01_ActorTests.actor_tests;

import actors.Actor;
import actors.Enemy;
import inventory.Inventory;

public class EnemyTest extends ActorTest {

	protected Actor createTestSubject() {
		return new Enemy(HEALTH, ATTACK, DEFENCE);
	}

	protected Actor createTestSubject(int health, int attack, int defence) {
		return new Enemy(health, attack, defence);
	}
	
	protected Actor createTestSubjectWithInventory() {
		Inventory i = new Inventory();
		i.add(ITEM);
		return new Enemy(HEALTH, ATTACK, DEFENCE, i);
	}
}