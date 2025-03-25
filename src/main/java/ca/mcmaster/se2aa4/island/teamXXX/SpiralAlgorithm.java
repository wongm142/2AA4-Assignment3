package ca.mcmaster.se2aa4.island.teamXXX;

import java.util.ArrayList;

import org.json.JSONObject;

public class SpiralAlgorithm extends Algorithm{

    private boolean complete = false;
    //private ArrayList<Coord> coords;
    Action actions;
    //private Drone drone;

    public SpiralAlgorithm(){
        //this.action = new Actions();
        //this.coords = coords;
        //this.drone = drone;
    }

    // private void initialize(ArrayList<Coord> coords){
    //     this.coords = coords;
    // }
    @Override
    public JSONObject run(Drone drone){
        boolean correctAction = false;
        boolean skip = false;
        while (!correctAction || skip){

            if (drone.getInfo().noCreek() == 0){
                complete = true;
            }
            correctAction = false;
            skip = false;
            switch (drone.getState()) {
                case 0: case 1: case 3:
                    if (drone.getSubCounter() == 0){
                        ActionWithParam action = new Heading();
                        action.doAction(drone.getDirection().seeLeft());
                        this.actions = action;
                        // this.action.heading(drone.getDirection().seeLeft()); //left
                        drone.updateDirection(drone.getDirection().seeLeft());
                        Coord coords = drone.getPosition();
                        coords.turnLeft();
                        drone.updateCoordinates(coords);
                        correctAction = true;
                        break;
                    }
                    else if(drone.getSubCounter() ==1){
                        if (drone.getExploredCoords().contains(drone.getPosition())){ //name of list of cords
                            correctAction = true;
                            skip = true;
                            break; 
                        }
                        ActionNoParam action = new Scan();
                        action.doAction();
                        this.actions = action;
                        // this.action.scan();
                        correctAction = true;
                        break;
                    }else{
                        correctAction = false;
                        break;
                    }
                    
                case 2:
                    correctAction = forwardCount(drone.getSubCounter(), drone.getCounter(), drone);
                    break;
               
                case 4:
                    correctAction = forwardCount(drone.getSubCounter()+2, drone.getCounter(), drone);
                    break;
            }
            if (!correctAction) {
                drone.setSubCounter(0);
                if (drone.getState() == 4) {
                    drone.setCounter(drone.getCounter()+1);
                    drone.setState(1);
                }else{
                    drone.setState(drone.getState()+1);
                }
            }
            else{
                drone.setSubCounter(drone.getSubCounter()+1);
            }
        }
        return actions.getDecision();
    }
    @Override
    public boolean isComplete(){
        return this.complete;
    }



    private boolean forwardCount(int subCounter, int counter, Drone drone){
        while (true){
            if(subCounter < counter*2){
                Coord coord = new Coord(drone.getPosition());
                if (subCounter % 2 == 0 ){
                    coord.flyForwards();
                    drone.updateCoordinates(coord);
                    ActionNoParam action = new Fly();
                    action.doAction();
                    this.actions = action;
                    // this.action.fly();
                    return true;
                }else if (drone.getExploredCoords().contains(drone.getPosition())) { //name of list of coords
                    subCounter +=1;
                    drone.setSubCounter(drone.getSubCounter()+1);
                }
                else{
                    ActionNoParam action = new Scan();
                    action.doAction();
                    this.actions = action;
                    // this.action.scan();
                    return true;
                }
            }else{
                return false;
            }
        }
    
    }

    // private ArrayList<String> populateResponse(int state, int subCounter, int counter, Direction direction){ 
    //     ArrayList<String> response = new ArrayList<>();
    //     response.add(action.getDecisionString());
    //     response.add(Integer.toString(state));
    //     response.add(Integer.toString(subCounter));
    //     response.add(Integer.toString(counter));
    //     response.add(direction.currentDirection.toString());
    //     return response;
    // }
}