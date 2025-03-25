package ca.mcmaster.se2aa4.island.teamXXX;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SearcherAlgorithmTest {
    
    @Test
    public void testSearcherAlgorithmInitialization() {
        SearcherAlgorithm searcher = new SearcherAlgorithm();
        Assertions.assertFalse(searcher.isComplete(), "Searcher should not be complete at initialization");
    }

}