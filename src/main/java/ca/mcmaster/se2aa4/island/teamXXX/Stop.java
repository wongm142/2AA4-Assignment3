package ca.mcmaster.se2aa4.island.teamXXX;

import org.json.JSONObject;

public class Stop implements ActionNoParam {
    private JSONObject decision;

    public Stop(){
        decision = new JSONObject();
    }

    @Override
    public void doAction() {
        decision.put("action", "stop");
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