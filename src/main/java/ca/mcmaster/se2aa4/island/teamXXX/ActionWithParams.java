package ca.mcmaster.se2aa4.island.teamXXX;

import org.json.JSONObject;

interface ActionWithParam {
   
    public void doAction(Direction direction);

    public JSONObject getDecision();

    public String getDecisionString();
    
    public void reset();
    
}
