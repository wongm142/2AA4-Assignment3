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
        Assertions.assertEquals(0, drone.getState());
        Assertions.assertEquals(0, drone.getSubCounter());
        Assertions.assertEquals(0, drone.getCounter());

        // Run the algorithm a 3 times
        algorithm.run(drone);
        algorithm.run(drone);
        algorithm.run(drone);
        
        Assertions.assertTrue(drone.getState() ==1, "after 3 runs after init state should be 1");
        Assertions.assertTrue(drone.getSubCounter() ==1, "after 3 runs after init subCounter should be 1");
        Assertions.assertTrue(drone.getCounter() == 0, "after 3 runs after init counter should be 1");
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

        Assertions.assertTrue(initialX == 0); //init
        Assertions.assertTrue(initialY == 0); //init
        Assertions.assertTrue(newX == -1); //left on x axis
        Assertions.assertTrue(newY == 1); //up on y axis

    }
}