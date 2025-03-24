package ca.mcmaster.se2aa4.island.teamXXX;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InfoTest {

    @Test
    public void infoInitTest() {
        JSONObject extras = new JSONObject();
        extras.put("creeks", new JSONArray());
        extras.put("biomes", new JSONArray().put("OCEAN"));
        extras.put("sites", new JSONArray());
        
        Info info = new Info(10, extras, "OK");
        
        Assertions.assertEquals(10, info.getCost());
        Assertions.assertEquals(extras, info.getExtras());
        Assertions.assertEquals("OK", info.getStatus());
    }


    @Test
    public void infoNoCreekTest_NoCreekPresent() {
        JSONObject extras = new JSONObject();
        extras.put("creeks", new JSONArray());

        Info info = new Info(10, extras, "OK");
        
        Assertions.assertEquals(1, info.noCreek(), "Expected 1 when no creeks are present");
    }

    @Test
    public void infoNoCreekTest_OneCreekPresent() {
        JSONObject extras = new JSONObject();
        JSONArray creeks = new JSONArray();
        creeks.put("CR12345");
        extras.put("creeks", creeks);

        Info info = new Info(10, extras, "OK");
        
        Assertions.assertEquals(0, info.noCreek(), "Expected 0 when exactly one creek is present");
    }

    @Test
    public void infoNoCreekTest_ExceptionHandling() {
        JSONObject extras = new JSONObject(); // No "creeks" key included

        Info info = new Info(10, extras, "OK");
        
        Assertions.assertEquals(2, info.noCreek(), "Expected 2 when an exception occurs (missing 'creeks' key)");
    }
}