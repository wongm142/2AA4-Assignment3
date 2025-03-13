package ca.mcmaster.se2aa4.island.teamXXX;

public class Battery {
    private int currBattery;

    public Battery(int currBattery){
        this.currBattery = currBattery;
    }

    public int getBattery(){
        return currBattery;
    }

    public void deductBattery(int amount){
        currBattery = currBattery - amount;
    }

}