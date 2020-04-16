package model;

public class Move {
    private Move move;
    private Field source, destination, captured;
    private boolean isCaptured;


    public Move(){
    }

    public Move(Move move, Field source, Field destination, Field captured, boolean isCaptured) {
        this.move = move;
        this.source = source;
        this.destination = destination;
        this.captured = captured;
        this.isCaptured = isCaptured;
    }

    public Field getSource() {
        return source;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public Field getDestination() {
        return destination;
    }

    public Field getCaptured() {
        return captured;
    }

    public boolean isCaptured() {
        return isCaptured;
    }

    public void setSource(Field source) {
        this.source = source;
    }

    public void setDestination(Field destination) {
        this.destination = destination;
    }

    public void setCaptured(Field captured) {
        this.captured = captured;
    }

    public void setCaptured(boolean captured) {
        isCaptured = captured;
    }

    @Override
    public String toString() {
        String nextMove = "";
        if(move == null){
            nextMove = "null";
        }else{
            nextMove = "not null";
        }
        String captureStr;
        if(isCaptured && captured != null){
            captureStr ="true Capture: (x: "+ captured.position.x+", y: "+captured.position.y+")";
        }else{
            captureStr = String.valueOf(isCaptured);
        }

        return "type: "+source.getPawnType()+" Src: (x: "+source.position.x+",y: "+source.position.y+")  Dst: (x: "+
                destination.position.x+", y: "+destination.position.y+") "+"Move: "+nextMove+"  isCapture: "+captureStr;
    }
}
