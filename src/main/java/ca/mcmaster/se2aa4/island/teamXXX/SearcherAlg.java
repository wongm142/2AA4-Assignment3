package ca.mcmaster.se2aa4.island.teamXXX;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class SearcherAlg {
    private final Logger logger = LogManager.getLogger();
    private IslandFinder finder = new IslandFinder();
    private boolean turnRightOnUTurn = finder.shouldTurnRightOnUTurn();
    private boolean searchingComplete = false;
    private SearcherAlgStates state;
    private Direction currDirection;
    private Coordinates coordinates;
    private Drone drone;
    private Info info;
    private Actions actions = new Actions();
    private ArrayList<PointOfInterest> CreeksAndEmergencySitesFound;
    private ArrayList<Coordinates> ExploredCoords;

    public SearcherAlg(){
        state = new InitialState();
    }
    
    public void setDrone(Drone drone, Info info, Coordinates coordinates, ArrayList<PointOfInterest> CreeksAndEmergencySitesFound, ArrayList<Coordinates> ExploredCoords){
        this.drone = drone;
        this.info = info;
        this.coordinates = coordinates;
        this.ExploredCoords = ExploredCoords;
        this.CreeksAndEmergencySitesFound = CreeksAndEmergencySitesFound;
    }

    public void setState(SearcherAlgStates state){
        this.state = state;
    }

    public boolean isComplete() {
        return searchingComplete;
    }

    public JSONObject search(Direction currDirection) {
        actions.reset();
        this.currDirection = currDirection;
        return state.handle(this);
    }

    private void moveAndUpdate(){
        actions.fly();
        coordinates.flyForwards();
        drone.updateCoordinates(coordinates);
    }

    private void checkPOI(JSONObject extras){ 
        JSONArray creeks = extras.getJSONArray("creeks");
        JSONArray sites = extras.getJSONArray("sites");

        if (creeks.length() > 0){
            String creekID = creeks.getString(0);
            CreeksAndEmergencySitesFound.add(new Creek(creekID, new Coordinates(drone.getPosition())));
        }

        if (sites.length() == 1){
            String siteID = sites.getString(0);
            CreeksAndEmergencySitesFound.add(0, new EmergencySite(siteID, new Coordinates(drone.getPosition())));
            logger.info("site found");
            searchingComplete = true;
        }
    }
    
    private void turnAndUpdate(Direction newDirection) {
        actions.heading(newDirection);

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
        public JSONObject handle(SearcherAlg searcher);
    }

    private class InitialState implements SearcherAlgStates {
        @Override
        public JSONObject handle(SearcherAlg searcher) {
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
                actions.scan();
                searcher.setState(new Scan());
            }

            // actions.scan();
            // searcher.setState(new Scan());

            return actions.getDecision();
        }
    }

    private class InitialTurn implements SearcherAlgStates {
        @Override
        public JSONObject handle(SearcherAlg searcher) {
            actions.scan();
            searcher.setState(new Scan());
            return actions.getDecision();
        }
    }

    private class Scan implements SearcherAlgStates {
        @Override
        public JSONObject handle(SearcherAlg searcher) {
            JSONObject extras = info.getExtras();
            JSONArray biomes = extras.getJSONArray("biomes");

            checkPOI(extras);
            ExploredCoords.add(new Coordinates(drone.getPosition()));

            if (biomes.length() == 1){
                if (biomes.get(0).equals("OCEAN")) {
                    actions.echo(currDirection);
                    searcher.setState(new EchoForward());
                    return actions.getDecision();
                }
            }

            moveAndUpdate();    

            searcher.setState(new InitialTurn());
            // searcher.setState(new InitialState());
            return actions.getDecision();
        }
    }

    private class EchoForward implements SearcherAlgStates {
        @Override
        public JSONObject handle(SearcherAlg searcher) {
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
    //     public JSONObject handle(SearcherAlg searcher){
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
        public JSONObject handle(SearcherAlg searcher){
            if (flyCount < range){
                moveAndUpdate();
                flyCount++;
            }

            else {
                actions.scan();
                searcher.setState(new Scan());
            }

            return actions.getDecision();
        }
    }

    private class UTurn implements SearcherAlgStates {
        private int turnCount;
        
        public UTurn() {
            turnCount = 1;
        }

        @Override
        public JSONObject handle(SearcherAlg searcher) {
            if (turnCount < 2){
                logger.info("** UTURNING {}", turnCount);
                logger.info("** SHOULD TURN RIGHT ON UTURN {}", turnRightOnUTurn);
                performUTurn(turnRightOnUTurn);
                turnCount++;
            }

            else {
                turnRightOnUTurn = !turnRightOnUTurn;
                actions.echo(currDirection);
                searcher.setState(new CheckInterlacedScanCompletion());
            }

            return actions.getDecision();
        }
    }

    private class CheckInterlacedScanCompletion implements SearcherAlgStates {
        @Override
        public JSONObject handle(SearcherAlg searcher) {
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

    //     public JSONObject handle(SearcherAlg searcher) {
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

        public JSONObject handle(SearcherAlg searcher) {
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
                    actions.echo(currDirection);
                    searcher.setState(new EchoForward());
                    turnRightOnUTurn = !turnRightOnUTurn;
                    // actions.stop();
                    break;
            }
            return actions.getDecision();
        }

    }
}