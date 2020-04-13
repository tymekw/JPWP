package model;

public class Field {
    private boolean isPawn;
    FieldType type;
    private PawnType pawn;
    Position position;

    public Field(FieldType type){
        this.type = type;
    }

    void setPawn(PawnType type){
        isPawn = true;
        this.pawn = type;
    }

    void removePawn(){
        isPawn = false;
        pawn = null;//for experimental purposes only
    }

    public PawnType getPawnType() {
        return pawn;
    }
    public boolean isPawn(){
        return isPawn;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
