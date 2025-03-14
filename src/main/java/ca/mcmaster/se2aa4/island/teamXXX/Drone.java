package ca.mcmaster.se2aa4.island.teamXXX;

import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Drone {
    private Direction currDirection;
    private Battery batteryLevel;
    private Coordinates currPosition;
    private Actions actions;

    public Drone (String direction, int initialBattery) {
        batteryLevel = new Battery(initialBattery);
        currDirection = Direction.valueOf(direction);
        currPosition = new Coordinates(0,0,currDirection);
    }
    
    public Direction getDirection(){
        return currDirection;
    }

    public int getBattery(){
        return batteryLevel.getBattery();
    }

    // public JSONObject beginExplore(){
    //     // search POI here and stuff
    //     return

    // }

    
}