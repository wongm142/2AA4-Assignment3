package ca.mcmaster.se2aa4.island.teamXXX;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DirectionTest {
    
    @Test
    public void testSeeLeft() {
        Assertions.assertEquals(Direction.W, Direction.N.seeLeft());
        Assertions.assertEquals(Direction.S, Direction.W.seeLeft());
        Assertions.assertEquals(Direction.E, Direction.S.seeLeft());
        Assertions.assertEquals(Direction.N, Direction.E.seeLeft());
    }

    @Test
    public void testSeeRight() {
        Assertions.assertEquals(Direction.E, Direction.N.seeRight());
        Assertions.assertEquals(Direction.S, Direction.E.seeRight());
        Assertions.assertEquals(Direction.W, Direction.S.seeRight());
        Assertions.assertEquals(Direction.N, Direction.W.seeRight());
    }

}