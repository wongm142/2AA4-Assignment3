package ca.mcmaster.se2aa4.island.teamXXX;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IslandFinderFindIslandTest {

    @Test
    public void testFindIslandContinuesMoving() {
        IslandFinder finder = new IslandFinder();
        Drone drone = new Drone(Direction.N, 100);

        JSONObject decision = finder.run(drone);

        Assertions.assertNotNull(decision, "Decision should not be null");
        Assertions.assertEquals("echo", decision.getString("action"), "FindIsland should call echo");
    }
}