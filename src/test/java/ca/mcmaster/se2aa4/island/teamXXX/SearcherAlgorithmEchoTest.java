package ca.mcmaster.se2aa4.island.teamXXX;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.json.JSONArray;

public class SearcherAlgorithmEchoTest {

    @Test
    public void testEcho() {
        SearcherAlgorithm searcher = new SearcherAlgorithm();
        Drone drone = new Drone(Direction.E, 100);
        JSONObject extras = new JSONObject();

        searcher.run(drone);

        extras.put("biomes", new JSONArray().put("OCEAN"));
        extras.put("creeks", new JSONArray());

        drone.receiveResponse(new Info(2, extras, "OK"));

        JSONObject decision = searcher.run(drone);

        Assertions.assertNotNull(decision, "Decision should not be null");
        Assertions.assertEquals("echo", decision.getString("action"), "Should echo");
    }
}