// package ca.mcmaster.se2aa4.island.teamXXX;

// import java.util.ArrayList;

// import org.json.JSONArray;
// import org.json.JSONObject;
// import org.junit.jupiter.api.Assertions;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import org.junit.jupiter.api.Test;


// public class ExampleTest {

//     @Test
//     public void batteryRetrieveTest(){
//         Battery battery = new Battery(50);
//         Assertions.assertEquals(50, battery.getBattery());
//     }
//     @Test
//     public void batteryDeductionTest(){
//         Battery battery = new Battery(50);
//         battery.deductBattery(10);
//         Assertions.assertEquals(40, battery.getBattery());
//     }

//     @Test
//     public void CoordsGetXTest(){
//         Coord coord = new Coord(1,5,Direction.N);
//         Assertions.assertEquals(1, coord.getX());
//     }

//     @Test
//     public void CoordsGetYTest(){
//         Coord coord = new Coord(1,5,Direction.N);
//         assertEquals(5, coord.getY());
//     }

//     @Test
//     public void CoordsRetrieveDirectionTest(){
//         Coord coord = new Coord(5,5,Direction.N);
//         Assertions.assertEquals(coord.getDirection(),Direction.N);
//     }

//     @Test
//     public void CoordsUpdateDirectionTest(){
//         Coord coord = new Coord(5,5,Direction.N);
//         coord.setDirection(Direction.S);
//         Assertions.assertEquals(coord.getDirection(),Direction.S);
//     }


//     @Test
//     public void CoordsEqualityTest(){
//         Coord coord1 = new Coord(5,5,Direction.N);
//         Coord coord2 = new Coord(5,5,Direction.S);
//         Assertions.assertEquals(coord1,coord2);
//     }

//     @Test
//     public void DirectionSeeLeftTest(){
//         Direction dir = Direction.N;
//         Assertions.assertEquals(dir.seeLeft(), Direction.W);
//     }

//     @Test
//     public void DirectionSeeRightTest(){
//         Direction dir = Direction.N;
//         Assertions.assertEquals(dir.seeRight(), Direction.E);
//     }

//     @Test
//     public void droneInitializationTest() {
//         Drone drone = new Drone(Direction.N, 100);
//         Assertions.assertEquals(Direction.N, drone.getDirection());
//         Assertions.assertEquals(100, drone.getBattery());
//         Assertions.assertEquals(new Coord(0, 0, Direction.N), drone.getPosition());
//     }

//     @Test
//     public void droneRecieveResponseTest() {
//         Drone drone = new Drone(Direction.N, 100);
//         JSONObject extras = new JSONObject();
//         extras.put("creeks", new JSONArray());
//         extras.put("biomes", new JSONArray().put("OCEAN"));
//         extras.put("sites", new JSONArray());
//         Info info = new Info(10, extras, "OK");
//         drone.receiveResponse(info);
//         Assertions.assertEquals("OK", drone.getInfo().getStatus());
//         Assertions.assertEquals(extras, drone.getInfo().getExtras());
//         Assertions.assertEquals(info, drone.getInfo());
//     }

//     @Test
//     public void droneBatteryUpdateTest() {
//         Drone drone = new Drone(Direction.N, 100);
//         JSONObject extras = new JSONObject();
//         extras.put("creeks", new JSONArray());
//         Info info = new Info(10, extras, "OK");
//         drone.receiveResponse(info);
//         Assertions.assertEquals(90, drone.getBattery());
//     }

//     @Test
//     public void droneDirectionUpdateTest() {
//         Drone drone = new Drone(Direction.N, 100);
//         drone.updateDirection(Direction.E);
//         Assertions.assertEquals(Direction.E, drone.getDirection());
//     }

//     @Test
//     public void dronePositionUpdateTest() {
//         Drone drone = new Drone(Direction.N, 100);
//         Coord newPosition = new Coord(2, 3, Direction.S);
//         drone.updateCoordinates(newPosition);
//         Assertions.assertEquals(newPosition, drone.getPosition());
//     }

//     @Test
//     public void droneStateManagementTest() {
//         Drone drone = new Drone(Direction.N, 100);
//         drone.setState(2);
//         drone.setCounter(5);
//         drone.setSubCounter(3);
//         Assertions.assertEquals(2, drone.getState());
//         Assertions.assertEquals(5, drone.getCounter());
//         Assertions.assertEquals(3, drone.getSubCounter());
//     }

//     @Test
//     public void droneAddCreekTest() {
//         Drone drone = new Drone(Direction.N, 100);
//         drone.addCreekToCreeksAndSites("CR12345");
//         ArrayList<PointOfInterest> creeks = drone.getCreeksAndEmergencySitesFound();
//         Assertions.assertEquals(1, creeks.size());
//         Assertions.assertTrue(creeks.get(0) instanceof Creek);
//     }

//     @Test
//     public void droneAddEmergencySiteTest() {
//         Drone drone = new Drone(Direction.N, 100);
//         drone.addSiteToCreeksAndSites("ES12345");
//         ArrayList<PointOfInterest> sites = drone.getCreeksAndEmergencySitesFound();
//         Assertions.assertEquals(1, sites.size());
//         Assertions.assertTrue(sites.get(0) instanceof EmergencySite);
//     }

//     @Test
//     public void droneExploredCoordsTest() {
//         Drone drone = new Drone(Direction.N, 100);
//         drone.addToCoords();
//         Assertions.assertEquals(1, drone.getExploredCoords().size());
//         Assertions.assertEquals(new Coord(0, 0, Direction.N), drone.getExploredCoords().get(0));
//     }









// }
