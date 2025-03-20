package ca.mcmaster.se2aa4.island.teamXXX;
 
 public enum Direction {
     N,
     E,
     S,
     W;
 
     public Direction seeLeft() {
         switch(this){
             case N:
                 return W;
             case W:
                 return S;
             case S:
                 return E;
             case E:
                 return N;
             default:
                 throw new IllegalStateException("Unexpected Value: " + this);
         }
      }
 
     public Direction seeRight(){
         switch(this){
             case N:
                 return E;
             case E:
                 return S;
             case S:
                 return W;
             case W:
                 return N;
             default:
                 throw new IllegalStateException("Unexpected Value: " + this);
         }
      }
 }