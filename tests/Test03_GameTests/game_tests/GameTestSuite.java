package Test03_GameTests.game_tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(

{ GameTest.class, GameMapTest.class, RoomTest.class })
public class GameTestSuite { // nothing
}
