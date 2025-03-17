package ca.mcmaster.se2aa4.island.teamXXX;

import org.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;

public class IslandFinder {
    private final Logger logger = LogManager.getLogger();
    private boolean findingComplete = false;
    private Direction currDirection;
    private Coordinates coordinates;
    private Drone drone;
    private Info info;
    private Actions actions = new Actions();
    private IslandFinderStates state;
    private ArrayList<DirectionRangePair> directions = new ArrayList<>();
    private int echoCount = 0;
    private int stepsSinceLastEcho = 0;

    public IslandFinder() {
        state = new InitialState();
    }

    public void setDrone(Drone drone, Info info, Coordinates coordinates) {
        this.drone = drone;
        this.info = info;
        this.coordinates = coordinates;
    }

    public void setState(IslandFinderStates state){
        this.state = state;
    }

    public boolean isComplete(){
        return findingComplete;
    }

    public JSONObject locateIsland(Direction currDirection){
        actions.reset();
        this.currDirection = currDirection;
        return state.handle(this);
    }

    private interface IslandFinderStates {
        JSONObject handle(IslandFinder finder);
    }

    private class DirectionRangePair {
        Direction.Directions direction;
        int range;
        String facing;

        DirectionRangePair(Direction.Directions direction, int range, String facing){
            this.direction = direction;
            this.range = range;
            this.facing = facing;
        }
    }

    private void addDirectionAndSort(Direction.Directions direction, int range, String facing){
        directions.add(new DirectionRangePair(direction, range, facing));
        directions.sort((a, b) -> Integer.compare(b.range, a.range));
    }

    private void moveAndUpdate(){
        actions.fly();
        coordinates.flyForwards();
        drone.updateCoordinates(coordinates);
    }
    
    private void turnAndUpdate(Direction.Directions newDirection) {
        actions.heading(newDirection);
        if (newDirection.equals(currDirection.seeLeft())) {
            coordinates.turnLeft();
            currDirection.turnLeft();
        }
        
        else {
            coordinates.turnRight();
            currDirection.turnRight();
        }

        drone.updateDirection(currDirection);
        drone.updateCoordinates(coordinates);
    }

    private class InitialState implements IslandFinderStates {
        @Override
        public JSONObject handle(IslandFinder finder){
            actions.echo(currDirection.getCurrentDirection());
            finder.setState(new InitialEcho());
            return actions.getDecision();
        }
    }

    private class InitialEcho implements IslandFinderStates {
        @Override
        public JSONObject handle(IslandFinder finder){
            JSONObject extras = info.getExtras();
            String finding = extras.getString("found");

            addDirectionAndSort(currDirection.getCurrentDirection(), extras.getInt("range"), "forwards");
            
            if (finding.equals("GROUND")){
                moveAndUpdate();
                finder.setState(new MoveToIsland(extras.getInt("range") - 1));
            }

            else {
                actions.echo(currDirection.seeRight());
                finder.setState(new InitialEchoRight());
            }

            return actions.getDecision();
        }
    }

    private class InitialEchoRight implements IslandFinderStates {
        @Override
        public JSONObject handle(IslandFinder finder){
            JSONObject extras = info.getExtras();
            String finding = extras.getString("found");

            addDirectionAndSort(currDirection.seeRight(), extras.getInt("range"), "right");

            if (finding.equals("GROUND")){
                turnAndUpdate(currDirection.seeRight());
                finder.setState(new MoveToIsland(extras.getInt("range") - 1));
            }

            else {
                actions.echo(currDirection.seeLeft());
                finder.setState(new InitialEchoLeft());
            }

            return actions.getDecision();
        }
    }

    private class InitialEchoLeft implements IslandFinderStates {
        @Override
        public JSONObject handle(IslandFinder finder){
            JSONObject extras = info.getExtras();
            String finding = extras.getString("found");

            if (finding.equals("GROUND")){
                turnAndUpdate(currDirection.seeLeft());
                finder.setState(new MoveToIsland(extras.getInt("range") - 1));
            }

            else {
                addDirectionAndSort(currDirection.seeLeft(), extras.getInt("range"), "left");

                Direction.Directions bestPath = directions.get(0).direction;
                if (directions.get(0).facing.equals("forwards")){
                    moveAndUpdate();
                }

                else {
                    turnAndUpdate(bestPath);
                }

                finder.setState(new FindIsland());

                logger.info("** Best path chosen: {} with range {} and facing {}", bestPath, directions.get(0).range, directions.get(0).facing);

            }

            return actions.getDecision();
        }
    }

    private class EchoRight implements IslandFinderStates {
        @Override
        public JSONObject handle(IslandFinder finder){
            JSONObject extras = info.getExtras();
            String finding = extras.getString("found");

            if (finding.equals("GROUND")){
                turnAndUpdate(currDirection.seeRight());
                finder.setState(new MoveToIsland(extras.getInt("range") - 1));
            }

            else {
                actions.echo(currDirection.seeLeft());
                finder.setState(new EchoLeft());
            }

            return actions.getDecision();
        }

    }

    private class EchoLeft implements IslandFinderStates {
        @Override
        public JSONObject handle(IslandFinder finder){
            JSONObject extras = info.getExtras();
            String finding = extras.getString("found");

            if (finding.equals("GROUND")){
                turnAndUpdate(currDirection.seeLeft());
                finder.setState(new MoveToIsland(extras.getInt("range") - 1));
            }

            else {
                moveAndUpdate();
                finder.setState(new FindIsland());
            }

            return actions.getDecision();
        }

    }

    private class FindIsland implements IslandFinderStates {
        @Override
        public JSONObject handle(IslandFinder finder){
            moveAndUpdate();
            stepsSinceLastEcho++;

            // echo after every 3 moves 
            if (stepsSinceLastEcho % 3 == 0){
                actions.echo(currDirection.seeRight());
                finder.setState(new EchoRight());
            }

            return actions.getDecision();
        }
    }

    private class MoveToIsland implements IslandFinderStates {
        private int stepsToIsland;

        public MoveToIsland(int range){
            this.stepsToIsland = range;
        }

        @Override
        public JSONObject handle(IslandFinder finder){
            if (stepsToIsland > 0){
                moveAndUpdate();
                stepsToIsland--;
                logger.info("** FLYING TO ISLAND");

            }

            else {
                actions.scan();
                findingComplete = true;

                logger.info("** FOUND ISLAND");

            }

            return actions.getDecision();
        }
    }
}