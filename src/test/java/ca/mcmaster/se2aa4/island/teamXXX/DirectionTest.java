package ca.mcmaster.se2aa4.island.teamXXX;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DirectionTest {

    @Test
    public void DirectionSeeLeftTest(){
        Direction dir = Direction.N;
        Assertions.assertEquals(dir.seeLeft(), Direction.W);
    }

    @Test
    public void DirectionSeeRightTest(){
        Direction dir = Direction.N;
        Assertions.assertEquals(dir.seeRight(), Direction.E);
    }
}