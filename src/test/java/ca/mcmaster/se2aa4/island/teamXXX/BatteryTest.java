package ca.mcmaster.se2aa4.island.teamXXX;

import java.beans.Transient;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BatteryTest {
    @Test
    public void batteryRetrieveTest(){
        Battery battery = new Battery(50);
        Assertions.assertEquals(50, battery.getBattery());
    }

    @Test
    public void batteryDeductionTest(){
        Battery battery = new Battery(50);
        battery.deductBattery(10);
        Assertions.assertEquals(40, battery.getBattery());
    }

    @Test
    public void testBatteryDeductionWithZero() {
        Battery battery = new Battery(50);
        battery.deductBattery(0);
        Assertions.assertEquals(50, battery.getBattery());
    }
}
