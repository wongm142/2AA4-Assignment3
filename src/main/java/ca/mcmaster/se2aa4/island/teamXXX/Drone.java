package ca.mcmaster.se2aa4.island.teamXXX;

import java.util.ArrayList;

public class Drone {
    //private final Logger logger = LogManager.getLogger();

    private Direction currDirection;
    private Battery batteryLevel;
    private Coord currPosition;
    //Spiral Alg instance variables and uses currDirection
    private Integer state = 0;
    private Integer counter = 0;
    private Integer subCounter = 0;
    private Info currInfo;
    private ArrayList<PointOfInterest> CreeksAndEmergencySitesFound = new ArrayList<>();
    private ArrayList<Coord> ExploredCoords = new ArrayList<>();

    public Drone(String direction, int initialBattery) {
        batteryLevel = new Battery(initialBattery);
        currDirection = Direction.valueOf(direction); 
        currPosition = new Coord(0, 0, currDirection);
    }

    public Drone(Direction direction, int initialBattery){
        batteryLevel = new Battery(initialBattery);
        currDirection = direction; 
        currPosition = new Coord(0, 0, currDirection);
    }

    public ArrayList<PointOfInterest> getCreeksAndEmergencySitesFound(){
        return CreeksAndEmergencySitesFound;
    }

    public ArrayList<Coord> getExploredCoords(){
        return ExploredCoords;
    }

    public void addCreekToCreeksAndSites(String creekID){
        CreeksAndEmergencySitesFound.add(new Creek(creekID, new Coord(currPosition)));
    }

    public void addSiteToCreeksAndSites(String siteID){
        CreeksAndEmergencySitesFound.add(0, new EmergencySite(siteID, new Coord(currPosition)));
    }

    public void addToCoords(){
        ExploredCoords.add(new Coord(currPosition));
    }

    public Direction getDirection(){
        return currDirection;
    }

    public Coord getPosition(){
        return currPosition;
    }

    public Info getInfo(){
        return currInfo;
    }

    public int getBattery() {
        return batteryLevel.getBattery();
    }

    public void updateDirection(Direction direction) {
        this.currDirection = direction;
    }

    public void updateCoordinates(Coord position) {
        this.currPosition = position;
    }

    public void receiveResponse(Info info) {
        batteryLevel.deductBattery(info.getCost());
        this.currInfo = info;
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
