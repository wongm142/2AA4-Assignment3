package ca.mcmaster.se2aa4.island.teamXXX;

import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import ca.mcmaster.se2aa4.island.teamXXX.DiscoveredPOIs;

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

    public SearcherAlg(){
        state = new InitialState();
    }
    
    public void setDrone(Drone drone, Info info, Coordinates coordinates){
        this.drone = drone;
        this.info = info;
        this.coordinates = coordinates;
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

        if (creeks.length() == 1){
            String creekID = creeks.getString(0);
            DiscoveredPOIs.CreeksAndEmergencySitesFound.add(new Creek(creekID, new Coordinates(coordinates.getX(), coordinates.getY(), currDirection)));
        }

        if (sites.length() == 1){
            String siteID = sites.getString(0);
            DiscoveredPOIs.CreeksAndEmergencySitesFound.add(0, new EmergencySite(siteID, new Coordinates(coordinates.getX(), coordinates.getY(), currDirection)));
            logger.info("site found");
            searchingComplete = true;
        }
    }
    
    private void turnAndUpdate(Direction newDirection) {
        actions.heading(newDirection);

        if (newDirection.equals(currDirection.seeLeft())) {
            logger.info("** TURNING LEFT");
            coordinates.turnLeft();
        }
        
        else {
            logger.info("TURNING RIGHT");
            coordinates.turnRight();
        }

        drone.updateDirection(newDirection);
        drone.updateCoordinates(coordinates);
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

            if (biomes.length() == 1){
                if (biomes.get(0).equals("OCEAN")) {
                    actions.echo(currDirection);
                    searcher.setState(new EchoForward());
                    return actions.getDecision();
                }
            }

            moveAndUpdate();
            searcher.setState(new InitialState());
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
                performUTurn(turnRightOnUTurn);
                searcher.setState(new UTurn());
            }

            return actions.getDecision();
        }
        
    }

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

            moveAndUpdate();

            if (finding.equals("GROUND")) {
                searcher.setState(new MoveToIsland(range));
            }

            else {
                searcher.setState(new PrepareForSweepTurn(range));
            }

            return actions.getDecision();
        }
    }

    private class PrepareForSweepTurn implements SearcherAlgStates {
        private int range;
        private int flyCount;
        private boolean turnRightOnSweepTurn = !turnRightOnUTurn;

        private PrepareForSweepTurn(int range) {
            this.range = range - 2;
            flyCount = 0;
        }

        public JSONObject handle(SearcherAlg searcher) {
            if (flyCount < range) {
                moveAndUpdate();
                flyCount++;
            }

            else {
                performUTurn(turnRightOnSweepTurn);
                searcher.setState(new SweepTurn());
            }

            return actions.getDecision();
        }
    }

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
                    performUTurn(turnRightOnSweepTurn);
                    step++;
                    break;
                case 3:
                    performUTurn(turnRightOnSweepTurn);
                    step++;
                    break;
                case 4:
                    performUTurn(turnRightOnUTurn);
                    step++;
                    break;
                case 5:
                    actions.echo(currDirection);
                    searcher.setState(new EchoForward());
                    break;
            }
            return actions.getDecision();
        }

    }
}