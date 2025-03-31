package ca.mcmaster.se2aa4.island.teamXXX;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SpiralAlgorithmTest {

    @Test
    public void testAlgorithmInitialization() {
        SpiralAlgorithm algorithm = new SpiralAlgorithm();
        Assertions.assertFalse(algorithm.isComplete(), "Algorithm should not be complete when init");
    }

    @Test
    public void testAlgorithmCompletesWhenCreekFound() {
        Drone drone = new Drone(Direction.N, 100);
        JSONObject extras = new JSONObject();
        extras.put("creeks", new JSONArray().put("CR12345")); // Simulate creek found
        drone.receiveResponse(new Info(10, extras, "OK"));

        SpiralAlgorithm algorithm = new SpiralAlgorithm();
        algorithm.run(drone);

        Assertions.assertTrue(algorithm.isComplete(), "Algorithm should be complete when a creek is found");
        Assertions.assertNotNull(algorithm.run(drone), "Decision should not be null when creek is found");
    }

    @Test
    public void testAlgorithmRunsWithoutFindingCreek() {
        Drone drone = new Drone(Direction.N, 100);
        JSONObject extras = new JSONObject();
        extras.put("creeks", new JSONArray()); // No creek found
        drone.receiveResponse(new Info(10, extras, "OK"));

        SpiralAlgorithm algorithm = new SpiralAlgorithm();
        JSONObject decision = algorithm.run(drone);

        Assertions.assertFalse(algorithm.isComplete(), "Algorithm should not be complete if no creek is found");
        Assertions.assertNotNull(decision, "There should be a decision to continue algorithm (!null)");
    }

    @Test
    public void testDroneStateTransitions() {
        Drone drone = new Drone(Direction.N, 100);
        JSONObject extras = new JSONObject();
        extras.put("creeks", new JSONArray()); // No creek found
        drone.receiveResponse(new Info(10, extras, "OK"));
        SpiralAlgorithm algorithm = new SpiralAlgorithm();

        // Initial state
        Assertions.assertEquals(0, drone.getState(), "Drone should be in state 0 initially");
        Assertions.assertEquals(0, drone.getSubCounter(), "Drone should have a subCounter of 0 initially");
        Assertions.assertEquals(0, drone.getCounter(), "Drone should have a counter of 0 initially");

        // Run the algorithm a 3 times
        algorithm.run(drone);
        algorithm.run(drone);
        algorithm.run(drone);
        
        Assertions.assertEquals(1, drone.getState(), "After 3 runs, drone should be in state 1");
        Assertions.assertEquals(1, drone.getSubCounter(), "After 3 runs, subCounter should be 1");
        Assertions.assertEquals(0, drone.getCounter(), "After 3 runs, counter should remain 0");
    }

    @Test
    public void testDroneCoordinateUpdate() {
        Drone drone = new Drone(Direction.N, 100);
        JSONObject extras = new JSONObject();
        extras.put("creeks", new JSONArray()); // No creek found
        drone.receiveResponse(new Info(10, extras, "OK"));
        SpiralAlgorithm algorithm = new SpiralAlgorithm();

        int initialX = drone.getPosition().getX();
        int initialY = drone.getPosition().getY();

        algorithm.run(drone); //first move of algorithm is to turn left

        int newX = drone.getPosition().getX();
        int newY = drone.getPosition().getY();

        Assertions.assertEquals(0, initialX, "Initial X coordinate should be 0");
        Assertions.assertEquals(0, initialY, "Initial Y coordinate should be 0");
        Assertions.assertEquals(-1, newX, "After left turn, X coordinate should be -1");
        Assertions.assertEquals(1, newY, "After left turn, Y coordinate should be 1");
    }
}