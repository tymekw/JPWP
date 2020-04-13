package model;

public class MoveDirectionRule implements Rule {
    @Override
    public boolean isValid(Field src, Field dst, Board board) {
        if(board.checkersManager.isAnyJump()) return true;
        PawnType myType = src.getPawnType();
        if (myType == PawnType.WHITE) {
            return canWhiteMove(src,dst);
        } else if (myType == PawnType.BLACK) {
            return canBlackMove(src, dst);
        }
        return true;
    }

    private boolean canWhiteMove(Field src, Field dst){
        if (dst.position.y == src.position.y + 1) {
            return (dst.position.x == src.position.x +1 || dst.position.x == src.position.x -1);
        }
        return false;
    }

    private boolean canBlackMove(Field src, Field dst){
        if (dst.position.y == src.position.y - 1) {
            return (dst.position.x == src.position.x +1 || dst.position.x == src.position.x -1);
        }
        return false;
    }

}