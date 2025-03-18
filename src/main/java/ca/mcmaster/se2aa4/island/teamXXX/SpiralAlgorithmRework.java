package ca.mcmaster.se2aa4.island.teamXXX;

import java.util.ArrayList;

public class SpiralAlgorithmRework {
    private Actions action;

    public SpiralAlgorithmRework(){
        this.action = new Actions();
    }

    public ArrayList<String> doAlgorithm(int state, int i, int counter, Direction direction){
        boolean correctAction = false;
        while (!correctAction){
            switch (state) {
                case 0: case 1: case 3:
                    if (i == 0){
                        this.action.heading(direction.seeLeft());
                        direction.turnLeft();
                        correctAction = true;
                        break;
                    }
                    else if(i ==1){
                        this.action.scan();
                        correctAction = true;
                        break;
                    }else{
                        correctAction = false;
                        break;
                    }
                    
                case 2:
                    correctAction = forwardCount(i, counter);
                    break;
               
                case 4:
                    correctAction = forwardCount(i+2, counter);
                    if (!correctAction){
                        counter+=1;
                    }
                    break;
            }
            if (!correctAction) {
                i = 0;
                if (state == 4) {
                    state  = 1;
                }else{
                    state +=1;
                }
            }
            else{
                i++;
            }
        }
        return populateResponse(state, i, counter, direction);
    }



    public boolean forwardCount(int i, int counter){
        if(i < counter*2){
            if (i % 2 == 0){
                this.action.fly();
                return true;
            }else{
                this.action.scan();
                return true;
            }
        }else{
            return false;
        }
    
    }

    private ArrayList<String> populateResponse(int state, int i, int counter, Direction direction){ 
        ArrayList<String> response = new ArrayList<>();
        response.add(action.getDecisionString());
        response.add(Integer.toString(state));
        response.add(Integer.toString(i));
        response.add(Integer.toString(counter));
        response.add(direction.currentDirection.toString());
        return response;
    }
}