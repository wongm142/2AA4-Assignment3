package ca.mcmaster.se2aa4.island.teamXXX;

import org.json.JSONObject;

public class Echo implements ActionWithParam {
    private JSONObject decision;
    public Echo(){
        decision = new JSONObject();
    }

    @Override
    public void execute(Direction direction) {
        decision.put("action", "echo");
        decision.put("parameters", (new JSONObject()).put("direction", direction.toString()));
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