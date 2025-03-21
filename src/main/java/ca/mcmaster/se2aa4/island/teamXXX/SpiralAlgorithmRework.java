package ca.mcmaster.se2aa4.island.teamXXX;

import java.util.ArrayList;

import org.json.JSONObject;



public class SpiralAlgorithmRework {
    private Actions action;
    private ArrayList<Coordinates> coords;
    //private Drone drone;

    public SpiralAlgorithmRework(ArrayList<Coordinates> coords){
        this.action = new Actions();
        this.coords = coords;
        //this.drone = drone;
    }

    public JSONObject doAlgorithm(Drone drone){
        boolean correctAction = false;
        while (!correctAction){
            switch (drone.getState()) {
                case 0: case 1: case 3:
                    if (drone.getSubCounter() == 0){
                        this.action.heading(drone.getDirection().seeLeft()); //left
                        drone.updateDirection(drone.getDirection().seeLeft());
                        Coordinates coords = drone.getCoordinate();
                        coords.turnLeft();
                        drone.updateCoordinates(coords);
                        correctAction = true;
                        break;
                    }
                    else if(drone.getSubCounter() ==1){
                        if (coords.contains(drone.getCoordinate())){
                            correctAction = false;
                            break;
                        }
                        
                        this.action.scan();
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
                    if (!correctAction){
                        drone.setCounter(drone.getCounter()+1);
                    }
                    break;
            }
            if (!correctAction) {
                drone.setSubCounter(0);
                if (drone.getState() == 4) {
                    drone.setState(1);
                }else{
                    drone.setState(drone.getState()+1);
                }
            }
            else{
                drone.setSubCounter(drone.getSubCounter()+1);
            }
        }
        return this.action.getDecision();
    }



    public boolean forwardCount(int subCounter, int counter, Drone drone){
        if(subCounter < counter*2){
            Coordinates coord = drone.getCoordinate();
            if (subCounter % 2 == 0 ){
                coord.flyForwards();
                drone.updateCoordinates(coord);
                this.action.fly();
                return true;
            }else if(this.coords.contains(coord)){
                coord.flyForwards();
                drone.updateCoordinates(coord);
                this.action.fly();
                drone.setSubCounter(drone.getSubCounter()+1);
                return true;
            }
            else{
                
                this.action.scan();
                return true;
            }
        }else{
            return false;
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