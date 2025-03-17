package ca.mcmaster.se2aa4.island.teamXXX;

import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Drone {
    private final Logger logger = LogManager.getLogger();

    private Direction currDirection;
    private Battery batteryLevel;
    private Coordinates currPosition;
    private Actions actions;

    private IslandFinder finder = new IslandFinder();
    private Info currInfo;

    public Drone(String direction, int initialBattery) {
        batteryLevel = new Battery(initialBattery);
        currDirection = new Direction(direction); 
        currPosition = new Coordinates(0, 0, currDirection);
    }

    public Direction.Directions getDirection() {
        return currDirection.getCurrentDirection();
    }

    public int getBattery() {
        return batteryLevel.getBattery();
    }

    public void updateDirection(Direction currDirection) {
        this.currDirection = currDirection;
    }

    public void updateCoordinates(Coordinates currPosition) {
        this.currPosition = currPosition;
    }

    public JSONObject beginExplore() {
        JSONObject decision;

        if (finder.isComplete()) {
            logger.info("** FOUND ISLAND");
            actions.stop();
            decision = actions.getDecision();
        } else {
            finder.setDrone(this, currInfo, currPosition);
            decision = finder.locateIsland(currDirection);
        }

        return decision;
    }

    public void receiveResponse(int cost, Info currInfo) {
        batteryLevel.deductBattery(cost);
        this.currInfo = currInfo;
    }
}
