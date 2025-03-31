package ca.mcmaster.se2aa4.island.teamXXX;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DirectionTest2 {

    @Test
    public void DirectionSeeLeftTest(){
        Direction dir = Direction.E;
        Assertions.assertEquals(dir.seeLeft(), Direction.N);
    }

    @Test
    public void DirectionSeeRightTest(){
        Direction dir = Direction.E;
        Assertions.assertEquals(dir.seeRight(), Direction.S);
    }
}