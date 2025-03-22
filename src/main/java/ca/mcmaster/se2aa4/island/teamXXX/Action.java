package ca.mcmaster.se2aa4.island.teamXXX;

import org.json.JSONObject;

interface Action {
   
    public void doAction();

    public JSONObject getDecision();

    public String getDecisionString();
    
    public void reset();
    
}