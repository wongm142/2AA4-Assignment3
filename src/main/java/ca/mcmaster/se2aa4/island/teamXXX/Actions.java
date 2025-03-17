package ca.mcmaster.se2aa4.island.teamXXX;

import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONObject;
import org.json.JSONTokener;

import ca.mcmaster.se2aa4.island.teamXXX.Direction.Directions;

public class Actions {
    private JSONObject decision;

    public Actions(){
        decision = new JSONObject();
    }

    public void fly(){
        decision.put("action", "fly");
    }

    public void scan(){
        decision.put("action", "scan");
    }

    public void echo(Directions direction){
        decision.put("action", "echo");
        decision.put("parameters", (new JSONObject()).put("direction", direction.toString()));
    }

    public void heading(Directions direction){
        decision.put("action", "heading");
        decision.put("parameters", new JSONObject().put("direction", direction.toString()));
    }

    public void stop(){
        decision.put("action", "stop");
    }

    public JSONObject getDecision(){
        return decision;
    }

    public String getDecisionString(){
        return decision.toString();
    }

    public void reset(){
        decision = new JSONObject();
    }

}