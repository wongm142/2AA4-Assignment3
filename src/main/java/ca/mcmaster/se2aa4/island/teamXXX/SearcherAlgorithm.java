package ca.mcmaster.se2aa4.island.teamXXX;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class SearcherAlgorithm extends Algorithm{
    private final Logger logger = LogManager.getLogger();
    private boolean turnRightOnUTurn = false;
    private boolean searchingComplete = false;
    private SearcherAlgStates state;
    private Direction currDirection;
    private Coord coordinates;
    private Drone drone;
    private Info info;
    private Action actions;
    private ActionNoParam action;
    private ActionWithParam actionWithParam;

    public SearcherAlgorithm(){
        state = new InitialState();
    }

    private void setState(SearcherAlgStates state){
        this.state = state;
    }
    @Override
    public boolean isComplete() {
        return searchingComplete;
    }
    @Override
    public JSONObject run(Drone drone) {
        this.drone = drone;
        this.info = drone.getInfo();
        this.coordinates = drone.getPosition();
        this.currDirection = drone.getDirection();
        return state.handle(this);
    }

    private void moveAndUpdate(){
        action = new Fly();
        action.doAction();
        actions = action;
        coordinates.flyForwards();
        drone.updateCoordinates(coordinates);
    }

    private void checkPOI(JSONObject extras){ 
        JSONArray creeks = extras.getJSONArray("creeks");
        JSONArray sites = extras.getJSONArray("sites");

        if (creeks.length() > 0){
            logger.info("ADDIGN CREEK TO ARRAYLIST");
            String creekID = creeks.getString(0);
            drone.addCreekToCreeksAndSites(creekID);
        }

        if (sites.length() == 1){
            String siteID = sites.getString(0);
            drone.addSiteToCreeksAndSites(siteID);
            logger.info("site found");
            searchingComplete = true;
        }
    }
    
    private void turnAndUpdate(Direction newDirection) {
        actionWithParam = new Heading();
        actionWithParam.doAction(newDirection);
        actions = actionWithParam;

        if (newDirection.equals(currDirection.seeLeft())) {
            logger.info("Turning left");
            coordinates.turnLeft();
        }
        
        else {
            logger.info("Turning Right");
            coordinates.turnRight();
        }

        drone.updateCoordinates(coordinates);
        drone.updateDirection(newDirection);
    }

    private void performUTurn(boolean turnRightOnUTurn) {
        if (turnRightOnUTurn) {
            turnAndUpdate(currDirection.seeRight());
        }

        else {
            turnAndUpdate(currDirection.seeLeft());
        }
    }

    public interface SearcherAlgStates {
        public JSONObject handle(SearcherAlgorithm searcher);
    }

    private class InitialState implements SearcherAlgStates {
        @Override
        public JSONObject handle(SearcherAlgorithm searcher) {

            if (currDirection == currDirection.S) {
                logger.info("TURNING LEFT");
                turnAndUpdate(currDirection.seeLeft());
                turnRightOnUTurn = true;
                searcher.setState(new InitialTurn());
            }

            else if (currDirection == currDirection.N) {
                logger.info("TURNIGN RIGHT");
                turnAndUpdate(currDirection.seeRight());
                turnRightOnUTurn = false;
                searcher.setState(new InitialTurn());
            }

            else{
                action = new Scan();
                action.doAction();
                searcher.setState(new ScanPlace());
                actions = action;
            }

            return actions.getDecision();
        }
    }

    private class InitialTurn implements SearcherAlgStates {
        @Override
        public JSONObject handle(SearcherAlgorithm searcher) {
            action = new Scan();
            action.doAction();
            searcher.setState(new ScanPlace());
            return action.getDecision();
        }
    }

    private class ScanPlace implements SearcherAlgStates {
        @Override
        public JSONObject handle(SearcherAlgorithm searcher) {
            JSONObject extras = info.getExtras();
            JSONArray biomes = extras.getJSONArray("biomes");

            checkPOI(extras);
            drone.addToCoords();

            if (biomes.length() == 1){
                if (biomes.get(0).equals("OCEAN")) {
                    actionWithParam = new Echo();
                    actionWithParam.doAction(currDirection);
                    searcher.setState(new EchoForward());
                    return actionWithParam.getDecision();
                }
            }

            moveAndUpdate();    

            searcher.setState(new InitialTurn());
            // searcher.setState(new InitialState());
            return action.getDecision();
        }
    }

    private class EchoForward implements SearcherAlgStates {
        @Override
        public JSONObject handle(SearcherAlgorithm searcher) {
            JSONObject extras = info.getExtras();
            String finding = extras.getString("found");
            int range = extras.getInt("range");

            if (finding.equals("GROUND")) {
                moveAndUpdate();
                searcher.setState(new MoveToIsland(range));
            }

            else {
                // moveAndUpdate();
                // searcher.setState(new MoveAwayFromIsland(range));
                performUTurn(turnRightOnUTurn);
                searcher.setState(new UTurn());
            }

            return actions.getDecision();
        }
        
    }

    // private class MoveAwayFromIsland implements SearcherAlgStates {
    //     private int range;
    //     private int flyCount;

    //     public MoveAwayFromIsland(int range){
    //         this.range = range;
    //         flyCount = 0;
    //     }

    //     @Override
    //     public JSONObject handle(SearcherAlgorithm searcher){
    //         if (flyCount < 4){
    //             moveAndUpdate();
    //             flyCount++;
    //         }

    //         else {
    //             performUTurn(turnRightOnUTurn);
    //             searcher.setState(new UTurn());
    //         }

    //         return actions.getDecision();
    //     }
    // }

    private class MoveToIsland implements SearcherAlgStates {
        private int range;
        private int flyCount;

        public MoveToIsland(int range){
            this.range = range;
            flyCount = 0;
        }

        @Override
        public JSONObject handle(SearcherAlgorithm searcher){
            if (flyCount < range){
                moveAndUpdate();
                flyCount++;
            }

            else {
                action = new Scan();
                action.doAction();
                searcher.setState(new ScanPlace());
            }

            return action.getDecision();
        }
    }

    private class UTurn implements SearcherAlgStates {
        private int turnCount;
        
        public UTurn() {
            turnCount = 1;
        }

        @Override
        public JSONObject handle(SearcherAlgorithm searcher) {
            if (turnCount < 2){
                logger.info("** UTURNING {}", turnCount);
                logger.info("** SHOULD TURN RIGHT ON UTURN {}", turnRightOnUTurn);
                performUTurn(turnRightOnUTurn);
                turnCount++;
            }

            else {
                turnRightOnUTurn = !turnRightOnUTurn;
                actionWithParam = new Echo();
                actionWithParam.doAction(currDirection);
                searcher.setState(new CheckInterlacedScanCompletion());
            }

            return actionWithParam.getDecision();
        }
    }

    private class CheckInterlacedScanCompletion implements SearcherAlgStates {
        @Override
        public JSONObject handle(SearcherAlgorithm searcher) {
            JSONObject extras = info.getExtras();
            String finding = extras.getString("found");
            int range = extras.getInt("range");

            if (finding.equals("GROUND")) {
                moveAndUpdate();
                searcher.setState(new MoveToIsland(range));
            }

            else {
                performUTurn(!turnRightOnUTurn);
                searcher.setState(new SweepTurn());
            }

            return actions.getDecision();
        }
    }

    // private class PrepareForSweepTurn implements SearcherAlgStates {
    //     private int range;
    //     private int flyCount;
    //     private boolean turnRightOnSweepTurn = !turnRightOnUTurn;

    //     private PrepareForSweepTurn(int range) {
    //         this.range = range - 2;
    //         flyCount = 0;
    //     }

    //     public JSONObject handle(SearcherAlgorithm searcher) {
    //         if (flyCount < range) {
    //             moveAndUpdate();
    //             flyCount++;
    //         }

    //         else {
    //             performUTurn(turnRightOnSweepTurn);
    //             searcher.setState(new SweepTurn());
    //         }

    //         return actions.getDecision();
    //     }
    // }

    private class SweepTurn implements SearcherAlgStates {
        private int step;
        private boolean turnRightOnSweepTurn = !turnRightOnUTurn;

        public SweepTurn() {
            step = 1;
        }

        public JSONObject handle(SearcherAlgorithm searcher) {
            logger.info("SWEEP TURN");
            switch(step) {
                case 1:
                    moveAndUpdate();
                    step++;
                    break;
                case 2:
                    moveAndUpdate();
                    step++;
                    break;
                case 3:
                    moveAndUpdate();
                    step++;
                    break;
                case 4:
                    performUTurn(turnRightOnSweepTurn);
                    step++;
                    break;
                case 5:
                    performUTurn(turnRightOnSweepTurn);
                    step++;
                    break;
                case 6:
                    performUTurn(turnRightOnSweepTurn);
                    step++;
                    break;
                case 7:
                    actionWithParam = new Echo();
                    actionWithParam.doAction(currDirection);
                    actions = actionWithParam;
                    searcher.setState(new EchoForward());
                    turnRightOnUTurn = !turnRightOnUTurn;
                    // actions.stop();
                    break;
            }
            return actions.getDecision();
        }

    }
}