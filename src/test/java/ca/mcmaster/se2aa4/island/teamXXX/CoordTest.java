package ca.mcmaster.se2aa4.island.teamXXX;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CoordTest {

    @Test
    public void coordsGetXTest(){
        Coord coord = new Coord(1,5,Direction.N);
        Assertions.assertEquals(1, coord.getX());
    }

    @Test
    public void coordsGetYTest(){
        Coord coord = new Coord(1,5,Direction.N);
        Assertions.assertEquals(5, coord.getY());
    }

    @Test
    public void coordsRetrieveDirectionTest(){
        Coord coord = new Coord(5,5,Direction.N);
        Assertions.assertEquals(coord.getDirection(),Direction.N);
    }

    @Test
    public void coordsUpdateDirectionTest(){
        Coord coord = new Coord(5,5,Direction.N);
        coord.setDirection(Direction.S);
        Assertions.assertEquals(coord.getDirection(),Direction.S);
    }

    @Test
    public void coordsEqualityTest(){
        Coord coord1 = new Coord(5,5,Direction.E);
        Coord coord2 = new Coord(5,5,Direction.E);
        Assertions.assertEquals(coord1, coord2);
    }

    @Test
    public void testEqualsDifferentCoords() {
        Coord coord1 = new Coord(10, 10, Direction.N);
        Coord coord2 = new Coord(5, 5, Direction.N);
        Assertions.assertNotEquals(coord1, coord2);
    }
        
    @Test
    public void testCoordsInit() {
        Coord coord = new Coord(10, 20, Direction.N);
        Assertions.assertEquals(10, coord.getX());
        Assertions.assertEquals(20, coord.getY());
        Assertions.assertEquals(Direction.N, coord.getDirection());
    }

    @Test
    public void testFlyForwards() {
        Coord coord = new Coord(0, 0, Direction.N);
        coord.flyForwards();
        Assertions.assertEquals(0, coord.getX());
        Assertions.assertEquals(1, coord.getY());
    }

    
    @Test
    public void testTurnLeft() {
        Coord coord = new Coord(0, 0, Direction.N);
        coord.turnLeft();
        Assertions.assertEquals(-1, coord.getX());
        Assertions.assertEquals(1, coord.getY());
        Assertions.assertEquals(Direction.W, coord.getDirection());
    }

    @Test
    public void testTurnRight() {
        Coord coord = new Coord(0, 0, Direction.N);
        coord.turnRight();
        Assertions.assertEquals(1, coord.getX());
        Assertions.assertEquals(1, coord.getY());
        Assertions.assertEquals(Direction.E, coord.getDirection());
    }
}