package ca.mcmaster.se2aa4.island.teamXXX;

public abstract class PointOfInterest {
    private final String id;
    Coord coord; 
    
    public PointOfInterest(String id, Coord coord){
        this.id = id;
        this.coord = coord;
    } 

    public String getId(){
        return this.id;
    }

    public double distanceFrom(PointOfInterest POI) {
        double dx = Math.abs(this.coord.getX() - POI.coord.getX());
        double dy = Math.abs(this.coord.getY() - POI.coord.getY());
        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    }

    public Coord getCord(){
        return this.coord;
    }
}
