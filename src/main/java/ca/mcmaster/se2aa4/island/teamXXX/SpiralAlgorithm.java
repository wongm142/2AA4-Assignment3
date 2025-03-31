package ca.mcmaster.se2aa4.island.teamXXX;

import java.util.ArrayList;

import javax.swing.Action;

import org.json.JSONObject;

public class SpiralAlgorithm extends Algorithm{

    private boolean complete = false;
    private ActionCommand actions;
    private ActionInvoker invoker = new ActionInvoker();

    public SpiralAlgorithm(){
        //this.action = new Actions();
        //this.coords = coords;
        //this.drone = drone;
    }

    @Override
    public JSONObject run(Drone drone){
        boolean correctAction = false;
        boolean skip = false;

        while (!correctAction || skip){

            if (drone.getInfo().noCreek() == 0){
                complete = true;

                Scan scanCommand = new Scan();
                invoker.setCommand(scanCommand);
                invoker.executeCommand();
                actions = scanCommand;

                return actions.getDecision();
            }

            correctAction = false;
            skip = false;

            switch (drone.getState()) {
                case 0: case 1: case 3:
                    if (drone.getSubCounter() == 0){
                        Heading headingCommand = new Heading();
                        invoker.setCommand(headingCommand);
                        invoker.executeCommand(drone.getDirection().seeLeft());
                        actions = headingCommand;

                        drone.updateDirection(drone.getDirection().seeLeft());
                        Coord coords = drone.getPosition();
                        coords.turnLeft();
                        drone.updateCoordinates(coords);
                        correctAction = true;
                        break;
                    }

                    else if(drone.getSubCounter() ==1) {
                        if (drone.getExploredCoords().contains(drone.getPosition())){ //name of list of cords
                            correctAction = true;
                            skip = true;
                            break; 
                        }

                        Scan scanCommand = new Scan();
                        invoker.setCommand(scanCommand);
                        invoker.executeCommand();
                        actions = scanCommand;

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

                    Fly flyCommand = new Fly();
                    invoker.setCommand(flyCommand);
                    invoker.executeCommand();
                    actions = flyCommand;

                    return true;
                }
                
                else if (drone.getExploredCoords().contains(drone.getPosition())) { //name of list of coords
                    subCounter +=1;
                    drone.setSubCounter(drone.getSubCounter()+1);
                }

                else{
                    Scan scanCommand = new Scan();
                    invoker.setCommand(scanCommand);
                    invoker.executeCommand();
                    actions = scanCommand;
                    return true;
                }
            }
            
            else{
                return false;
            }
        }
    }
}