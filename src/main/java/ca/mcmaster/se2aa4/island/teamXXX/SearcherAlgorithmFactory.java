package ca.mcmaster.se2aa4.island.teamXXX;

public class SearcherAlgorithmFactory extends AlgorithmFactory {
    @Override
    public Algorithm createAlgorithm(){ 
        return new SearcherAlgorithm();
    };
}