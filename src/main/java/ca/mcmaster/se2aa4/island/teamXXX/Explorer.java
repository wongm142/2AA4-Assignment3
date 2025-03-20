package ca.mcmaster.se2aa4.island.teamXXX;

import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();
    private Drone drone;
    private Translator trans = new Translator();
    private IslandFinder finder = new IslandFinder();
    private Actions actions;
    private SearcherAlg searcher = new SearcherAlg();

    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}",info.toString(2));
        String direction = info.getString("heading");
        Integer batteryLevel = info.getInt("budget");

        drone = new Drone(direction, batteryLevel);

        logger.info("The drone is facing {}", direction);
        logger.info("Battery level is {}", batteryLevel);
    }

    @Override
    public String takeDecision() {
        JSONObject decision = new JSONObject();
        
        if (drone.getBattery() < 35 || searcher.isComplete()){
            actions.stop(); 
            decision = actions.getDecision();
        }

        else {
            if (finder.isComplete()) {
                logger.info("** SEARCHING ISLAND");
                logger.info("** CURRENT HEADING {}", drone.getDirection());
                // actions.stop(); 
                // decision = actions.getDecision();
                searcher.setDrone(drone, drone.getInfo(), drone.getPosition());
                decision = searcher.search(drone.getDirection());
            } 
            
            else {
                finder.setDrone(drone, drone.getInfo(), drone.getPosition());
                decision = finder.locateIsland(drone.getDirection());
            }
        }

        logger.info("** Decision: {}", decision.toString());
        return decision.toString();
    }

    @Override
    public void acknowledgeResults(String s) {
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Response received:\n"+response.toString(2));
        Info information = trans.translate(response);

        Integer cost = information.getCost();
        logger.info("The cost of the action was {}", cost);
        String status = information.getStatus();
        logger.info("The status of the drone is {}", status);
        JSONObject extraInfo = information.getExtras();
        logger.info("Additional information received: {}", extraInfo);

        drone.receiveResponse(cost, information);
    }

    @Override
    public String deliverFinalReport() {
        int finalBattery = drone.getBattery();
        logger.info("** FINAL REPORT");
        logger.info("Final Battery level {}", finalBattery);

        return "no creek found";
    }

}
