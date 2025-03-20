package ca.mcmaster.se2aa4.island.teamXXX;

public abstract class PointOfInterest {
    private final String id;
    Coordinates coord; 
    
    public PointOfInterest(String id, Coordinates coord){
        this.id = id;
        this.coord = coord;
    } 

    public String getId(){
        return this.id;
    }

    public double distanceFrom(PointOfInterest POI) {
        double dx = this.coord.getX() - POI.coord.getX();
        double dy = this.coord.getY() - POI.coord.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    private Coordinates getCord(){
        return this.coord;
    }
}
