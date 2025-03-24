package ca.mcmaster.se2aa4.island.teamXXX;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IslandFinderTest {
    
    @Test
    public void testIslandFinderInitialization() {
        IslandFinder finder = new IslandFinder();
        Assertions.assertFalse(finder.isComplete(), "Island finding should not be complete at initialization");
    }

}