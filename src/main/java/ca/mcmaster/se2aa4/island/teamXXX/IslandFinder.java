package ca.mcmaster.se2aa4.island.teamXXX;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class IslandFinder {
    private final Logger logger = LogManager.getLogger();
    private boolean turnRightOnUTurn;
    private boolean findingComplete = false;
    private Direction currDirection;
    private Coord coordinates;
    private Drone drone;
    private Info info;
    private Action actions;
    private ActionNoParam action;
    private ActionWithParam actionWithParam;
    private IslandFinderStates state;
    private ArrayList<DirectionRangePair> directions = new ArrayList<>();
    private int echoCount = 0;
    private int stepsSinceLastEcho = 0;

    public IslandFinder() {
        state = new InitialState();
    }

    public void setDrone(Drone drone, Info info, Coord coordinates) {
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

    public boolean shouldTurnRightOnUTurn() {
        return turnRightOnUTurn;
    }

    public JSONObject locateIsland(Direction currDirection){
        this.currDirection = currDirection;
        return state.handle(this);
    }

    private interface IslandFinderStates {
        JSONObject handle(IslandFinder finder);
    }

    private class DirectionRangePair {
        Direction direction;
        int range;
        String facing;

        DirectionRangePair(Direction direction, int range, String facing){
            this.direction = direction;
            this.range = range;
            this.facing = facing;
        }
    }

    private void addDirectionAndSort(Direction direction, int range, String facing){
        directions.add(new DirectionRangePair(direction, range, facing));
        directions.sort((a, b) -> Integer.compare(b.range, a.range));
    }

    private void moveAndUpdate(){
        action = new Fly();
        action.doAction();
        actions = action;
        coordinates.flyForwards();
        drone.updateCoordinates(coordinates);
    }
    
    private void turnAndUpdate(Direction newDirection) {
        actionWithParam = new Heading();
        actionWithParam.doAction(newDirection);
        actions = actionWithParam;

        if (newDirection.equals(currDirection.seeLeft())) {
            coordinates.turnLeft();
            turnRightOnUTurn = true;
        }
        
        else {
            coordinates.turnRight();
            turnRightOnUTurn = false;
        }

        logger.info("UPDATED DIRECTION {}\n", currDirection);
        
        drone.updateDirection(newDirection);
        drone.updateCoordinates(coordinates);
    }

    private class InitialState implements IslandFinderStates {
        @Override
        public JSONObject handle(IslandFinder finder){
            actionWithParam = new Echo();
            actionWithParam.doAction(currDirection);
            actions = actionWithParam;
            finder.setState(new InitialEcho());

            // actions.scan();
            return actions.getDecision();
        }
    }

    private class InitialEcho implements IslandFinderStates {
        @Override
        public JSONObject handle(IslandFinder finder){
            JSONObject extras = info.getExtras();
            String finding = extras.getString("found");

            addDirectionAndSort(currDirection, extras.getInt("range"), "forwards");
            
            if (finding.equals("GROUND")){
                moveAndUpdate();
                finder.setState(new MoveToIsland(extras.getInt("range") - 1));
            }

            else {
                actionWithParam = new Echo();
                actionWithParam.doAction(currDirection.seeRight());
                actions = actionWithParam;
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
                actionWithParam = new Echo();
                actionWithParam.doAction(currDirection.seeLeft());
                actions = actionWithParam;
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

                Direction bestPath = directions.get(0).direction;
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
                actionWithParam = new Echo();
                actionWithParam.doAction(currDirection.seeLeft());
                actions = actionWithParam;
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
            // moveAndUpdate();
            stepsSinceLastEcho++;

            // echo after every 1 move 
            if (stepsSinceLastEcho % 1 == 0){
                actionWithParam = new Echo();
                actionWithParam.doAction(currDirection.seeRight());
                actions = actionWithParam;
                finder.setState(new EchoRight());
            }

            return actions.getDecision();
        }
    }

    private class MoveToIsland implements IslandFinderStates {
        private int range;
        private int flyCount;

        public MoveToIsland(int range){
            this.range = range;
            flyCount = 0;
        }

        @Override
        public JSONObject handle(IslandFinder finder){

            if (flyCount < range + 2){
                moveAndUpdate();
                flyCount++;
                logger.info("** CURRENT HEADING {}", currDirection);
                logger.info("** FLYING TO ISLAND");

            }

            else {
                action = new Scan();
                action.doAction();
                actions = action;
                findingComplete = true;

                logger.info("** FOUND ISLAND");

            }

            return actions.getDecision();
        }
    }
}