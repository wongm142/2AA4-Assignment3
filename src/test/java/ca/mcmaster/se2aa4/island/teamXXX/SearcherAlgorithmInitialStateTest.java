package ca.mcmaster.se2aa4.island.teamXXX;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SearcherAlgorithmInitialStateTest {

    @Test
    public void testInitialState() {
        SearcherAlgorithm searcher = new SearcherAlgorithm();
        Drone drone = new Drone(Direction.E, 100);

        JSONObject decision = searcher.run(drone);
        
        Assertions.assertNotNull(decision, "Decision should not be null");
        Assertions.assertEquals("scan", decision.getString("action"), "Searcher should call scan");
    }
}