package ca.mcmaster.se2aa4.island.teamXXX;

public class IslandFinderFactory extends AlgorithmFactory {
    @Override
    public Algorithm createAlgorithm(){ 
        return new IslandFinder();
    };
}