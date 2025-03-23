package ca.mcmaster.se2aa4.island.teamXXX;

public class Coord {
    private int x;
    private int y;
    private Direction direction;

    public Coord(int x, int y, Direction direction){
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
    public Coord(Coord coord){
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
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Coord that = (Coord) obj;
        return x == that.x && y == that.y;
    }

}