package ca.mcmaster.se2aa4.island.teamXXX;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BatteryTest2 {
    @Test
    public void batteryRetrieveTest(){
        Battery battery = new Battery(60);
        Assertions.assertEquals(60, battery.getBattery());
    }

    @Test
    public void batteryDeductionTest(){
        Battery battery = new Battery(60);
        battery.deductBattery(10);
        Assertions.assertEquals(50, battery.getBattery());
    }
}
