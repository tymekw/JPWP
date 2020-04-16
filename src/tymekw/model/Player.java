package model;

public class Player {
    PawnType pawn, pawnKing;
    PlayerType playerType;

    public Player(PawnType pawn, PawnType pawnKing, PlayerType playerType) {
        this.pawn = pawn;
        this.pawnKing = pawnKing;
        this.playerType = playerType;
    }

    public boolean isMyPawn(PawnType pawn){
        return pawn == this.pawn || pawn == pawnKing;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }
}
