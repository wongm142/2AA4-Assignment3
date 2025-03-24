package ca.mcmaster.se2aa4.island.teamXXX;

//import java.security.AlgorithmConstraints;

import org.json.JSONObject;

public abstract class Algorithm {
    
    public Algorithm(){

    }
    public abstract JSONObject run(Drone drone);

    public abstract boolean isComplete();

    
}
