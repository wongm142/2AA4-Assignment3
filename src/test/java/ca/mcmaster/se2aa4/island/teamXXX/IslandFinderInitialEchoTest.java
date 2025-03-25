package ca.mcmaster.se2aa4.island.teamXXX;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IslandFinderInitialEchoTest {

    @Test
    public void testInitialEchoGroundFound() {
        IslandFinder finder = new IslandFinder();
        Drone drone = new Drone(Direction.N, 100);
        JSONObject extras = new JSONObject();
        extras.put("found", "GROUND");
        extras.put("range", 2);
        drone.receiveResponse(new Info(10, extras, "OK"));

        JSONObject decision = finder.run(drone);
        decision = finder.run(drone);

        Assertions.assertNotNull(decision, "Decision should not be null");
        Assertions.assertEquals("fly", decision.getString("action"), "Should move towards the island");
    }
}
