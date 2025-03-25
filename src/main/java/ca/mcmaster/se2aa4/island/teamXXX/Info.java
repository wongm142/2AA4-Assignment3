package ca.mcmaster.se2aa4.island.teamXXX;

import org.json.JSONArray;
import org.json.JSONObject;

public class Info {
    private int cost;
    private JSONObject extras;
    private String droneStatus;

    public Info (int cost, JSONObject extras, String droneStatus) {
        this.cost = cost;
        this.extras = extras;
        this.droneStatus = droneStatus;
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

     public int noCreek(){
      
        try{
            JSONArray creeks = extras.getJSONArray("creeks");
            if (creeks.length() == 1){
                // String creekID = creeks.getString(0);
                // DiscoveredPOIs.CreeksAndEmergencySitesFound.add(new Creek(creekID, new Coordinates(coordinates.getX(), coordinates.getY(), currDirection)));
                return 0;
            }
            else{
                return 1;
            }
        }
        catch (Exception e){
            return 2;
        }
    }

}