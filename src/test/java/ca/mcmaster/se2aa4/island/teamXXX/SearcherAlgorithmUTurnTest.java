package ca.mcmaster.se2aa4.island.teamXXX;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.json.JSONArray;

public class SearcherAlgorithmUTurnTest {

    @Test
    public void testUTurn() {
        SearcherAlgorithm searcher = new SearcherAlgorithm();
        Drone drone = new Drone(Direction.E, 100);

        JSONObject extras = new JSONObject();
        searcher.run(drone);

        extras.put("biomes", new JSONArray().put("OCEAN"));
        extras.put("creeks", new JSONArray());

        drone.receiveResponse(new Info(2, extras, "OK"));
        extras.put("sites", new JSONArray());

        searcher.run(drone);

        extras = new JSONObject();
        extras.put("found", "OUT_OF_RANGE");
        extras.put("range", 10);
        drone.receiveResponse(new Info(10, extras, "OK"));

        JSONObject decision = searcher.run(drone);

        Assertions.assertNotNull(decision, "Decision should not be null");
        Assertions.assertEquals("heading", decision.getString("action"), "Should turn");
    }
}