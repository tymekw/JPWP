package model;

import java.util.LinkedList;
import java.util.List;

public class JumpAnalyzer {
    Board board;
    CheckersManager checkersManager;

    public JumpAnalyzer(Board board, CheckersManager checkersManager) {
        this.board = board;
        this.checkersManager = checkersManager;
    }

    boolean isAnyJump(){
        for(int i=0; i<board.ROWS; i++){
            for(int j=0; j<board.COLS;j++){
                if(checkersManager.isPawnInTurn(board.getField(i,j)) && board.getField(i,j).isPawn()){
                    if(canPawnJump(board.getField(i,j))){
                        return true;
                    }
                }
            }
        }
        return false;
    }

     boolean canPawnJump(Field field){
        int x = field.position.x;
        int y = field.position.y;
        int row_limit = board.ROWS-1;
        int column_limit = board.COLS-1;

        for (int i = Math.max(0, x - 1); i <= Math.min(x + 1, row_limit); i++) {
            for (int j = Math.max(0, y - 1); j <= Math.min(y + 1, column_limit); j++) {
                //if (i != x && j != y) {//only corners
                if(canJumpThroughNeighbor(field,board.getField(i,j))){
                    return true;
                }
                //}
            }
        }
        return  false;
    }

    private boolean canJumpThroughNeighbor(Field src, Field neighbor){
        int directionX = neighbor.position.x - src.position.x;
        int directionY = neighbor.position.y - src.position.y;

        int dstX = neighbor.position.x + directionX;
        int dstY = neighbor.position.y + directionY;
        PawnType type = src.getPawnType();

        if (!isNeighborPrey(neighbor.position.x, neighbor.position.y)) return false;
        if (!isDestinationAvailable(dstX, dstY)) return false;
        if(type == PawnType.BLACK_KING || type == PawnType.WHITE_KING) return true;
        if(!isJumpInProperDirection(src,directionY)) return false;

        return true;
    }

    boolean isJumpInProperDirection(Field src, int directionY){
        if(src.getPawnType() == PawnType.BLACK && directionY > 0) return false;
        if(src.getPawnType() == PawnType.WHITE && directionY < 0) return false;
        return true;
    }

    List<Field> getJumpedOverPawns(Field src, Field dst){
        List<Field> pawnsFound = new LinkedList<>();
        int iStart = Math.min(src.position.x,dst.position.x)+1;//+1 żeby ominąć startową pozycje
        int jStart = Math.min(src.position.y,dst.position.y)+1;
        int condition = Math.max(src.position.x,dst.position.x);
        int j=jStart;
        for(int i=iStart; i<condition; i++,j++){
                if(board.getField(i,j).isPawn()){
                    pawnsFound.add(board.getField(i,j));
                }
        }
        return pawnsFound;
    }

    boolean isJumpDiagonal(Field src, Field dst){
        return getJumpLengthX(src, dst) ==  getJumpLengthY(src, dst);
    }

    int getJumpLengthX(Field src, Field dst){
        return Math.abs(src.position.x - dst.position.x);
    }

    int getJumpLengthY(Field src, Field dst){
        return  Math.abs(src.position.y - dst.position.y);
    }

    boolean isJump(Field src, Field dst){
        return (getJumpLengthX(src,dst)>1 || getJumpLengthY(src, dst)>1);
    }

    boolean isDestinationAvailable(int dstX, int dstY){
        if(dstX < 0 || dstX > board.ROWS-1) return false;//-1 bo to index w tablicy
        if(dstY < 0 || dstY > board.COLS-1) return false;
        if(board.getField(dstX,dstY).isPawn()) return false;
        return true;
    }

    boolean isNeighborPrey(int preyX, int preyY){
        Field prey = board.getField(preyX,preyY);
        return prey.isPawn() && !checkersManager.isPawnInTurn(prey);
    }
}
