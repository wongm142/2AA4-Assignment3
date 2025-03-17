package ca.mcmaster.se2aa4.island.teamXXX;

import org.json.JSONObject;

public class Info {
    private int cost;
    private JSONObject extras;
    private Direction direction;
    private String droneStatus;

    public Info (int cost, JSONObject extras, String droneStatus) {
        this.cost = cost;
        this.extras = extras;
        this.droneStatus = droneStatus;
    }

    public Direction getDirection(){
        return direction;
    }

    public JSONObject getExtras(){
        return extras;
    }

    public int getCost(){
        return cost;
    }

    public String getStatus(){
        return droneStatus;
    }

}