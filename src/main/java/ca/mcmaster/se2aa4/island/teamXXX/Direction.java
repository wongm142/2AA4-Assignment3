package ca.mcmaster.se2aa4.island.teamXXX;

public class Direction {
    public enum Directions { N, E, S, W }

    public Directions currentDirection;

    public Direction(Directions initialDirection) {
        this.currentDirection = initialDirection;
    }
    public Direction(String initialDirection) {
        if (initialDirection.equals("N")) {
            this.currentDirection = Directions.N;
        } else if (initialDirection.equals("E")) {
            this.currentDirection = Directions.E;
        } else if (initialDirection.equals("S")) {
            this.currentDirection = Directions.S;
        } else if (initialDirection.equals("W")) {
            this.currentDirection = Directions.W;
        }
    }

    // Turn left and update the current direction
    public void turnLeft() {
        switch (currentDirection) {
            case N:
                currentDirection = Directions.W;
                break;
            case W:
                currentDirection = Directions.S;
                break;
            case S:
                currentDirection = Directions.E;
                break;
            case E:
                currentDirection = Directions.N;
                break;
        }
    }

    // Turn right and update the current direction
    public void turnRight() {
        switch (currentDirection) {
            case N:
                currentDirection = Directions.E;
                break;
            case E:
                currentDirection = Directions.S;
                break;
            case S:
                currentDirection = Directions.W;
                break;
            case W:
                currentDirection = Directions.N;
                break;
        }
    }

    // See what is to the left without changing the current direction
    public Directions seeLeft() {
        switch (currentDirection) {
            case N:
                return Directions.W;
            case W:
                return Directions.S;
            case S:
                return Directions.E;
            case E:
                return Directions.N;
        }
        return currentDirection; // Fallback, though should never happen
    }

    // See what is to the right without changing the current direction
    public Directions seeRight() {
        switch (currentDirection) {
            case N:
                return Directions.E;
            case E:
                return Directions.S;
            case S:
                return Directions.W;
            case W:
                return Directions.N;
        }
        return currentDirection; // Fallback, though should never happen
    }

    public Directions getCurrentDirection() {
        return currentDirection;
    }
}
