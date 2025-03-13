package ca.mcmaster.se2aa4.island.teamXXX;

public class Coordinates {
    private int xCoord;
    private int yCoord;
    private Direction direction;

    public Coordinates(int xCoord, int yCoord, Direction direction){
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.direction = direction;
    }

    public int getXCoords(){
        return xCoord;
    }

    public int getYCoords(){
        return yCoord;
    }

    public Direction getDirection(){
        return direction;
    }

    public void setDirection (Direction direction){
        this.direction = direction;
    }

    public void flyForwards(){
        switch (direction){
            case N:
                yCoord++;
                break;
            case S:
                yCoord--;
                break;
            case E:
                xCoord++;
                break;
            case W:
                xCoord--;
                break;
        }
    }

    public void turnLeft(){
        switch (direction){
            case N:
                yCoord++;
                xCoord--;
                break;
            case S:
                yCoord--;
                xCoord++;
                break;
            case E:
                xCoord++;
                yCoord++;
                break;
            case W:
                xCoord--;
                yCoord--;
                break;
        }

        this.direction = direction.leftDirection();
    }

    public void turnRight(){
        switch (direction){
            case N:
                yCoord++;
                xCoord++;
                break;
            case S:
                yCoord--;
                xCoord--;
                break;
            case E:
                xCoord++;
                yCoord--;
                break;
            case W:
                xCoord--;
                yCoord++;
                break;
        }

        this.direction = direction.rightDirection();
    }

}