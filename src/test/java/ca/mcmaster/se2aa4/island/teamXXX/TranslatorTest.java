package ca.mcmaster.se2aa4.island.teamXXX;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.json.JSONArray;

public class TranslatorTest {

    @Test
    public void testTranslator() {
        JSONObject response = new JSONObject();
        response.put("cost", 2);

        JSONObject extras = new JSONObject();
        extras.put("biomes", new JSONArray().put("BEACH").put("FOREST"));
        extras.put("creeks", new JSONArray());
        extras.put("sites", new JSONArray());

        response.put("extras", extras);
        response.put("status", "OK");

        Translator translator = new Translator();
        
        Info result = translator.translate(response);

        Assertions.assertNotNull(result, "Info object should not be null");
        Assertions.assertEquals(2, result.getCost(), "Cost should be 2");
        Assertions.assertEquals("OK", result.getStatus(), "Status should be 'OK'");

        Assertions.assertNotNull(result.getExtras(), "Extras should not be null");

        JSONArray expectedBiomes = new JSONArray().put("BEACH").put("FOREST");
        Assertions.assertEquals(expectedBiomes.toString(), result.getExtras().get("biomes").toString(), "Biomes should match the input");
        
        Assertions.assertTrue(result.getExtras().getJSONArray("creeks").isEmpty(), "Creeks should be empty");
        Assertions.assertTrue(result.getExtras().getJSONArray("sites").isEmpty(), "Sites should be empty");
    }

    @Test
    public void testTranslatorWithEmptyResponse() {
        JSONObject response = new JSONObject();
        
        response.put("cost", 0);
        response.put("status", "OK");
        response.put("extras", new JSONObject());
        
        Translator translator = new Translator();
        Info result = translator.translate(response);
        
        Assertions.assertNotNull(result, "Info object should not be null");
        Assertions.assertEquals(0, result.getCost(), "Cost should be 0");
        
        Assertions.assertEquals("OK", result.getStatus(), "Status should be 'OK'");

        Assertions.assertNotNull(result.getExtras(), "Extras should not be null");
        Assertions.assertTrue(result.getExtras().isEmpty(), "Extras should be empty");
    }
}