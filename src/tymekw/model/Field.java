package model;

public class Field {
    private boolean isPawn;
    FieldType type;
    private PawnType pawn;
    Position position;

    public Field(){
    }

    public Field(FieldType type){
        this.type = type;
    }

    void setPawn1(PawnType type){
        setPawnBoolean(true);
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

    public void setPawnBoolean(boolean pawn) {
        isPawn = pawn;
    }

    public void setType(FieldType type) {
        this.type = type;
    }

    public FieldType getType() {
        return type;
    }

    public PawnType getPawn() {
        return pawn;
    }

    public Position getPosition() {
        return position;
    }
}
