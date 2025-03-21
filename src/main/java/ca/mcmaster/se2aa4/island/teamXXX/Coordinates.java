package ca.mcmaster.se2aa4.island.teamXXX;

import com.vividsolutions.jts.geom.Coordinate;
import java.util.Objects;

public class Coordinates {
    private int x;
    private int y;
    private Direction direction;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Coordinates that = (Coordinates) obj;
        return x == that.x && y == that.y && direction == that.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, direction);
    }

    public Coordinates(int x, int y, Direction direction){
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
    public Coordinates(Coordinates coord){
        this.x = coord.getX();
        this.y = coord.getY();
        this.direction = coord.getDirection();
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
        switch (direction){
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
        switch (direction){
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

        this.direction = direction.seeLeft();
    }

    public void turnRight(){
        switch (direction){
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

        this.direction = direction.seeRight();
    }

}