package ca.mcmaster.se2aa4.island.teamXXX;

import java.util.ArrayList;

public class SpiralAlgorithm extends Algorithm {

    public SpiralAlgorithm(){
        super();
    }
    
    public ArrayList<String> doAlgorithm(int state, int i, int revolution, Direction direction){
  
        boolean correct_action = false;
        
        while (!correct_action) {
            switch (state) {
                case 0: //move to top right corner of new revolution
                    if (revolution == 0) {
                        correct_action = corner1(i, direction);
                    } else {
                        correct_action = corner2(i, direction);
                    }
                    break; // Exit switch, then check while condition
                case 1: case 4: case 7: case 10:  // Move to beginning of next sweep of spiral (ex North -> South Sweep into East -> West Sweep)
                    correct_action = leftSpin(i, direction);
                    break;
                case 2: case 5: case 8: case 11: // Move forward to next unscanned area (continue sweep)
                    correct_action = forwardBig(i, revolution);
                    break;
                case 3: case 6: case 9: // Move forward to next unscanned area -1 and position your self for next sweep of spiral (final step of sweep)
                    correct_action = forwardSmall(i);
                    break;
                }
            if (!correct_action) {
                i = 0;
                if (state == 11) {
                    revolution +=1;
                } 
                state = (state + 1) % 12 ;  // Move to next state
            }
            else{
                i++;
            }
        }
        return populateResponse(state, i, revolution, direction);
    }

    public boolean corner1(int i, Direction direction){
        switch (i) {
            case 0:
                this.action.heading(direction.seeRight());
                direction.turnRight();
                break;
            case 1:
                this.action.fly();
                break;
            case 2:
                this.action.heading(direction.seeLeft());
                direction.turnLeft();
                break;
            default:
                return false;
            
        }
        return true;
    }

    public boolean corner2(int i, Direction direction){
        switch (i) {
            case 0:
                this.action.heading(direction.seeRight());
                direction.turnRight();
                break;
            case 1: case 3:
                this.action.fly();
                break;
            case 2:
                this.action.heading(direction.seeLeft());
                direction.turnLeft();
                break;
            default:
                return false;
        }
        return true;
    }
    public boolean leftSpin(int i, Direction direction){
        switch (i) {
            case 0:
                this.action.heading(direction.seeLeft());
                direction.turnLeft();
                break;
            case 1: case 2:
                this.action.fly();
                break;
            default:
                return false;
        }
        return true;
    }

    public boolean forwardSmall(int i){
        switch (i) {
            case 0: case 1:
                this.action.fly();
                break;
            default:
                return false;
        }
        return true;
    }

    public boolean forwardBig(int i, int revolution){
        if(i < revolution*2 ){
            this.action.fly();
            return true;
        }else{
            return false;
        }
    }
    private ArrayList<String> populateResponse(int state, int i, int revolution, Direction direction){ 
        ArrayList<String> response = new ArrayList<>();
        response.add(action.getDecisionString());
        response.add(Integer.toString(state));
        response.add(Integer.toString(i));
        response.add(Integer.toString(revolution));
        response.add(direction.currentDirection.toString());
        return response;
    }
}
   
