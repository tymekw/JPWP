package model;

public class PawnInTurnRole implements Rule {
    @Override
    public boolean isValid(Field src, Field dst, Board board) {
        return CheckersManager.currentPlayer.isMyPawn(src.getPawnType());
    }
}
