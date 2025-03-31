package ca.mcmaster.se2aa4.island.teamXXX;

import org.json.JSONObject;

public class Heading implements ActionWithParam {
    private JSONObject decision;

    public Heading(){
        decision = new JSONObject();
    }

    @Override
    public void execute(Direction direction) {
        decision.put("action", "heading");
        decision.put("parameters", new JSONObject().put("direction", direction.toString()));
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