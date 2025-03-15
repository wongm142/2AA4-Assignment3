package ca.mcmaster.se2aa4.island.teamXXX;

import java.util.ArrayList;

public abstract class Algorithm {
    protected Actions action;   
    public Algorithm(){
        this.action = new Actions();
    }

    public abstract ArrayList<String> doAlgorithm(int state, int i, int j, Direction direction);
}
