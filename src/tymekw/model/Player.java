package model;

public class Player {
    PawnType pawn, pawnKing;

    public Player(PawnType pawn, PawnType pawnKing) {
        this.pawn = pawn;
        this.pawnKing = pawnKing;
    }

    public boolean isMyPawn(PawnType pawn){
        return pawn == this.pawn || pawn == pawnKing;
    }
}
