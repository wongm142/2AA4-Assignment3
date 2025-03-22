package ca.mcmaster.se2aa4.island.teamXXX;

import org.json.JSONObject;

public class Scan implements ActionNoParam {
    private JSONObject decision;

    public Scan(){
        decision = new JSONObject();
    }

    @Override
    public void doAction() {
        decision.put("action", "scan");
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