package ca.mcmaster.se2aa4.island.teamXXX;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.json.JSONArray;

public class SearcherAlgorithmMoveToIslandTest {
    @Test
    public void testUTurn() {
        SearcherAlgorithm searcher = new SearcherAlgorithm();
        Drone drone = new Drone(Direction.E, 100);

        searcher.run(drone);
        searcher.run(drone);

        JSONObject extras = new JSONObject();
        extras.put("biomes", new JSONArray().put("OCEAN"));
        drone.receiveResponse(new Info(2, extras, "OK"));

        searcher.run(drone);

        extras = new JSONObject();
        extras.put("found", "GROUND");
        extras.put("range", 10);
        drone.receiveResponse(new Info(10, extras, "OK"));

        JSONObject decision = searcher.run(drone);

        Assertions.assertNotNull(decision, "Decision should not be null");
        Assertions.assertEquals("fly", decision.getString("action"), "Should fly to island");
    }
}
