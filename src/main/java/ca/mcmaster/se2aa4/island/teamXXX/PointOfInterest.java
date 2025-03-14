package ca.mcmaster.se2aa4.island.teamXXX;

public abstract class PointOfInterest {
    private final String id;
    Coordinates coord; 
    
    public PointOfInterest(String id, Coordinates coord){
        this.id = id;
        this.coord = coord;
    } 

    private String getId(){
        return this.id;
    }

    public int distanceFrom(PointOfInterest POI){
        return (Math.abs(this.coord.getX() - POI.coord.getX()) + Math.abs(this.coord.getY() - POI.coord.getY()));
    }

    private Coordinates getCord(){
        return this.coord;
    }
}
