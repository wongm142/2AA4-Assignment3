package ca.mcmaster.se2aa4.island.teamXXX;

import java.io.StringReader;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import eu.ace_design.island.bot.IExplorerRaid;

public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();
    private Drone drone;
    private Translator trans = new Translator();
    private Algorithm finder = new IslandFinder();
    private Action actions;

    private int stage = 0;
    private boolean foundCreekUsingSpiral = false;
    
    private Algorithm searcher = new SearcherAlgorithm();
    private Algorithm spiral = new SpiralAlgorithm();
    

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

        boolean foundMove = false;

        while (!foundMove){
            if (drone.getBattery() < 35){
                logger.info("low battery, returning now");
                stage = 3;
            }

            logger.info("re run");
            JSONObject decision = new JSONObject();
            foundMove = false;

            if (stage == 0){
                if (finder.isComplete()) {
                    logger.info("** FOUND ISLAND");
                    //actions.stop();
                    stage = 1;
                    //decision = actions.getDecision();
                } else {
                    // actions.scan();
                    // decision = actions.getDecision();
                    decision = finder.run(drone);
                    logger.info("** Decision: {}", decision.toString());
                    return decision.toString();
                }
                
            }else if(stage == 1){
                logger.info("Stage 1");
                // checkPOIs(drone.getPosition(), drone.getCreeksAndEmergencySitesFound()
                if(searcher.isComplete()){
                    logger.info("going to stage 2");
                    // spiral.initialize(drone.getExploredCoords());
                    stage = 2;
                }
        
                else {
                    if (finder.isComplete()) {
                        logger.info("** SEARCHING ISLAND");
                        logger.info("** CURRENT HEADING {}", drone.getDirection());
                        // actions.stop(); 
                        // decision = actions.getDecision();
                        decision = searcher.run(drone);
                    } 
                    
                    else {
                        decision = finder.run(drone);
                    }
                }

                if (stage != 2){
                    logger.info("** Decision: {}", decision.toString());
                    return decision.toString();
                }
              

            }else if(stage == 2){
                logger.info("Stage 2");
        
                logger.info("Searching for creek");
                logger.info("** State: {}", drone.getState());
                logger.info("** SubCount: {}", drone.getSubCounter());
                logger.info("**Counter: {}" ,drone.getCounter());
                decision = spiral.run(drone);
                logger.info("** Decision: {}", decision.toString());
                if (spiral.isComplete() || checkPOIs(drone.getPosition(), drone.getCreeksAndEmergencySitesFound())){
                    logger.info("is complete");
                    JSONArray creeks = drone.getInfo().getExtras().getJSONArray("creeks");
                    logger.info("1");
                    String creekID = creeks.getString(0);
                    logger.info("2");
                    drone.addCreekToCreeksAndSites(creekID);
                    logger.info("3");
                    foundCreekUsingSpiral = true;
                    stage =3;
                }
                // logger.info("**")

                return decision.toString();
                
                
                // else{
                //     logger.info("Add creek to list ELSE");
                //     JSONArray creeks = drone.getInfo().getExtras().getJSONArray("creeks");
                //     logger.info("1");
                //     String creekID = creeks.getString(0);
                //     logger.info("2");
                //     drone.addCreekToCreeksAndSites(creekID);
                //     logger.info("3");
                //     foundCreekUsingSpiral = true;

                //     //add creek found to list
                //     stage = 3;
                // }
                
            }

            else{ //stage 3
                logger.info("Stage 3");
                decision = new JSONObject();
                ActionNoParam action = new Stop();
                action.doAction();
                decision = action.getDecision();
                logger.info("** Decision: {}",decision);
                return decision.toString();
            }

        }
        logger.info("should never be here");
        return null;

    }

    @Override
    public void acknowledgeResults(String s) {
        logger.info("** COORDS X, Y {} {}", drone.getPosition().getX(), drone.getPosition().getY());
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Response received:\n"+response.toString(2));
        Info information = trans.translate(response);

        Integer cost = information.getCost();
        logger.info("The cost of the action was {}", cost);
        String status = information.getStatus();
        logger.info("The status of the drone is {}", status);
        JSONObject extraInfo = information.getExtras();
        logger.info("Additional information received: {}", extraInfo);

        drone.receiveResponse(information);
    }

    @Override
    public String deliverFinalReport() {
        int finalBattery = drone.getBattery();
        logger.info("** FINAL REPORT");
        logger.info("Final Battery level {}", finalBattery);

        PointOfInterest closestCreek = findClosestCreek();
    
        return closestCreek.getId();
    }

    private boolean checkPOIs(Coord coords, ArrayList<PointOfInterest> CreeksAndEmergencySitesFound){ 
        for (PointOfInterest poi : CreeksAndEmergencySitesFound){
            if (coords.equals(poi.getCord())){
                return true;
            }
        }

        return false;
    }

    private PointOfInterest findClosestCreek() {
        PointOfInterest closestCreek = null;
        ArrayList <PointOfInterest> CreeksAndEmergencySitesFound = drone.getCreeksAndEmergencySitesFound();
        double minDistance = Integer.MAX_VALUE;

        if (!CreeksAndEmergencySitesFound.isEmpty() && CreeksAndEmergencySitesFound.get(0) instanceof EmergencySite) {
            EmergencySite emergencySite = (EmergencySite) CreeksAndEmergencySitesFound.get(0);
            logger.info("Emergency Site coords {} {}", emergencySite.getCord().getX(), emergencySite.getCord().getY());
            logger.info("** SITE FOUND ");
            for (PointOfInterest poi : CreeksAndEmergencySitesFound) {
                if (poi instanceof Creek) {
                    double distance = poi.distanceFrom(emergencySite);
                    logger.info(poi.getId());
                    logger.info(distance);
                    logger.info("POI COORDS {} {}", poi.getCord().getX(), poi.getCord().getY());

                    if (distance < minDistance){
                        minDistance = distance;
                        closestCreek = poi;
                    }
                }
            }

            if (foundCreekUsingSpiral) {
                closestCreek = CreeksAndEmergencySitesFound.get(CreeksAndEmergencySitesFound.size() - 1);
            }
        }

        if (closestCreek != null) {
            logger.info("** Closest Creek Found: {} at distance {}", closestCreek.getId(), minDistance);
            return closestCreek;
        }

        for (PointOfInterest poi : CreeksAndEmergencySitesFound) {
            if (poi instanceof Creek) {
                logger.info("** No emergency site found. Returning random creek.");
                return poi;
            }
        }

        logger.info("** No creeks found");
        return null;

    }

}
