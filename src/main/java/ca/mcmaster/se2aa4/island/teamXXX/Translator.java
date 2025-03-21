package ca.mcmaster.se2aa4.island.teamXXX;

import org.json.JSONObject;

public class Translator {

    public Info translate(JSONObject response) {
        Info info = new Info(getCost(response), getExtras(response), getStatus(response));
        return info;
    }

    private Integer getCost(JSONObject response){
        Integer cost = response.getInt("cost");
        return cost;
    }

    private JSONObject getExtras(JSONObject response){
        JSONObject extras = response.getJSONObject("extras");
        return extras;
    }

    private String getStatus(JSONObject response){
        String status = response.getString("status");
        return status;
    }
    
}