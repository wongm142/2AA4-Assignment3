package ca.mcmaster.se2aa4.island.teamXXX;

import org.json.JSONObject;

public interface Action {
    public JSONObject getDecision();

    public String getDecisionString();

    public void reset();
}