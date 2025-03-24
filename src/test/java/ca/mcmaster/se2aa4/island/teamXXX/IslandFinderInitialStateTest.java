package ca.mcmaster.se2aa4.island.teamXXX;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IslandFinderInitialStateTest {

    @Test
    public void testInitialStateEcho() {
        IslandFinder finder = new IslandFinder();
        JSONObject decision = finder.run(new Drone(Direction.N, 100));

        Assertions.assertNotNull(decision, "Decision should not be null in InitialState");
        Assertions.assertEquals("echo", decision.getString("action"), "InitialState should start with an echo");
    }
}