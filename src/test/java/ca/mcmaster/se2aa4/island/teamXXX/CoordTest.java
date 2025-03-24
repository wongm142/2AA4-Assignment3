package ca.mcmaster.se2aa4.island.teamXXX;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CoordTest {

    @Test
    public void CoordsGetXTest(){
        Coord coord = new Coord(1,5,Direction.N);
        Assertions.assertEquals(1, coord.getX());
    }

    @Test
    public void CoordsGetYTest(){
        Coord coord = new Coord(1,5,Direction.N);
        Assertions.assertEquals(5, coord.getY());
    }

    @Test
    public void CoordsRetrieveDirectionTest(){
        Coord coord = new Coord(5,5,Direction.N);
        Assertions.assertEquals(coord.getDirection(),Direction.N);
    }

    @Test
    public void CoordsUpdateDirectionTest(){
        Coord coord = new Coord(5,5,Direction.N);
        coord.setDirection(Direction.S);
        Assertions.assertEquals(coord.getDirection(),Direction.S);
    }

    @Test
    public void CoordsEqualityTest(){
        Coord coord1 = new Coord(5,5,Direction.N);
        Coord coord2 = new Coord(5,5,Direction.S);
        Assertions.assertEquals(coord1,coord2);
    }
}