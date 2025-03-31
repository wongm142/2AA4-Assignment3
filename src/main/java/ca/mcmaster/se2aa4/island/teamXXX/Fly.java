package ca.mcmaster.se2aa4.island.teamXXX;

import org.json.JSONObject;

public class Fly implements ActionNoParam {
    private JSONObject decision;
    public Fly(){
        decision = new JSONObject();
    }

    @Override
    public void execute() {
        decision.put("action", "fly");
    }
    @Override
    public JSONObject getDecision(){
        return decision;
    }
    @Override
    public String getDecisionString(){
        return decision.toString();
    }
    @Override
    public void reset(){
        decision = new JSONObject();
    }
    
}