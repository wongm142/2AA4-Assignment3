package ca.mcmaster.se2aa4.island.teamXXX;

import java.io.StringReader;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;

import org.json.JSONObject;
import org.json.JSONTokener;

import com.vividsolutions.jts.geom.Coordinate;

public class Drone {
    //private final Logger logger = LogManager.getLogger();

    private Direction currDirection;
    private Battery batteryLevel;
    private Coordinates currPosition;
    //Spiral Alg instance variables and uses currDirection
    private Integer state = 0;
    private Integer counter = 0;
    private Integer subCounter = 0;
    
    private Info currInfo;


    public Drone(String direction, int initialBattery) {
        batteryLevel = new Battery(initialBattery);
        currDirection = Direction.valueOf(direction); 
        currPosition = new Coordinates(0, 0, currDirection);
    }

    public Direction getDirection(){
        return currDirection;
    }

    public Coordinates getPosition(){
        return currPosition;
    }

    public Info getInfo(){
        return currInfo;
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

    public void receiveResponse(int cost, Info currInfo) {
        batteryLevel.deductBattery(cost);
        this.currInfo = currInfo;
    }
    public int getState(){
        return this.state;
    }

    public int getCounter(){
        return this.counter;
    }

    public int getSubCounter(){
        return this.subCounter;
    }

    public Coordinates getCoordinate() {
        return this.currPosition;
    }

    // public void setSpiralParams(ArrayList<String> params){
    //     //0 is the decision
    //     setState(Integer.parseInt(params.get(1)));
    //     setSubCounter(Integer.parseInt(params.get(2))); 
    //     setCounter(Integer.parseInt(params.get(3)));         
    //     currDirection.setCurrentDirection(params.get(4));
    // }

    public void setState(int state){
        this.state = state;
    }
    public void setCounter(int counter){
        this.counter = counter;
    }
    public void setSubCounter(int subCounter){
        this.subCounter = subCounter;
    }
}
