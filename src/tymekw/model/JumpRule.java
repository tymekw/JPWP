package model;

public class JumpRule implements Rule {
    JumpAnalyzer analyzer;
    //CheckersManager checkersManager;
    @Override
    public boolean isValid(Field src, Field dst, Board board) {
        analyzer  = new JumpAnalyzer(board);
        //checkersManager = board.checkersManager;
        PawnType type = src.getPawnType();
        if(!analyzer.isJump(src, dst)) return true;
        //if(type == PawnType.WHITE_KING || type == PawnType.BLACK_KING) return true;
        if(!isValidJump(src, dst)) return false;
        return true;
    }

    private boolean isValidJump(Field src, Field dst){
        int preyX = (src.position.x + dst.position.x) / 2;
        int preyY = (src.position.y + dst.position.y) / 2;
        int dirY = preyY - src.position.y;
        System.out.println(1);
        if(!analyzer.isJumpDiagonal(src,dst)) return false;
        System.out.println(2);
        if(analyzer.getJumpLengthX(src,dst) != 2) return false;
        System.out.println(3);
        if(!analyzer.isNeighborPrey(preyX,preyY)) return false;
        System.out.println(4);
        if(!analyzer.isJumpInProperDirection(src,dirY)) return false;
        System.out.println(5);
        if(!analyzer.isDestinationAvailable(dst.position.x,dst.position.y)) return false;
        System.out.println(6);
        if(!isValidMultiJump(src)) return false;
        System.out.println(7);

        return true;
    }

    boolean isValidMultiJump(Field src){
        Move info = CheckersManager.lastMoveInfo;
        Field lastMoveDestination = info.getDestination();
        boolean isPartOfMultiJump = CheckersManager.currentPlayer.isMyPawn(lastMoveDestination.getPawnType());
        if(isPartOfMultiJump){
            return lastMoveDestination.equals(src);
        }
        return true;
    }
}
