package ca.mcmaster.se2aa4.island.teamXXX;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CoordTest2 {

    @Test
    public void CoordsGetXTest(){
        Coord coord = new Coord(5,5,Direction.N);
        Assertions.assertEquals(5, coord.getX());
    }

    @Test
    public void CoordsGetYTest(){
        Coord coord = new Coord(5,10,Direction.N);
        Assertions.assertEquals(10, coord.getY());
    }

    @Test
    public void CoordsRetrieveDirectionTest(){
        Coord coord = new Coord(5,5,Direction.E);
        Assertions.assertEquals(coord.getDirection(), Direction.E);
    }

    @Test
    public void CoordsUpdateDirectionTest(){
        Coord coord = new Coord(5,5,Direction.E);
        coord.setDirection(Direction.W);
        Assertions.assertEquals(coord.getDirection(), Direction.W);
    }

    @Test
    public void CoordsEqualityTest(){
        Coord coord1 = new Coord(10, 10, Direction.N);
        Coord coord2 = new Coord(10, 10, Direction.N);
        Assertions.assertEquals(coord1, coord2);
    }
}