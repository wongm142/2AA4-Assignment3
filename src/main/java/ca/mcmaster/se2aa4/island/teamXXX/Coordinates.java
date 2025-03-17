package ca.mcmaster.se2aa4.island.teamXXX;

import ca.mcmaster.se2aa4.island.teamXXX.Direction.Directions;

public class Coordinates {
    private int x;
    private int y;
    private Direction direction;

    public Coordinates(int x, int y, Direction direction){
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public Direction getDirection(){
        return direction;
    }

    public void setDirection (Direction direction){
        this.direction = direction;
    }

    public void flyForwards(){
        switch (direction.currentDirection){
            case N:
                y++;
                break;
            case S:
                y--;
                break;
            case E:
                x++;
                break;
            case W:
                x--;
                break;
        }
    }

    public void turnLeft(){
        switch (direction.currentDirection){
            case N:
                y++;
                x--;
                break;
            case S:
                y--;
                x++;
                break;
            case E:
                x++;
                y++;
                break;
            case W:
                x--;
                y--;
                break;
        }

        this.direction.turnLeft();
    }

    public void turnRight(){
        switch (direction.currentDirection){
            case N:
                y++;
                x++;
                break;
            case S:
                y--;
                x--;
                break;
            case E:
                x++;
                y--;
                break;
            case W:
                x--;
                y++;
                break;
        }

        this.direction.turnLeft();
    }

}