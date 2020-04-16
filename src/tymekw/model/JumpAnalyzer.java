package model;

import java.util.LinkedList;
import java.util.List;

public class JumpAnalyzer {
    Board board;
    Player player;
    //CheckersManager checkersManager;

    public JumpAnalyzer(Board board) {
        this.board = board;
        player = CheckersManager.currentPlayer;
    }

    public JumpAnalyzer(Board board, Player player) {
        this.board = board;
        this.player = player;
    }

    boolean isAnyJump(){
        return !getAvailableJumps().isEmpty();
    }

    List<Move> getAvailableJumps(){
        List<Move> availableJumps = new LinkedList<>();
        for(int i=0; i<board.ROWS; i++){
            for(int j=0; j<board.COLS;j++){
                if(player.isMyPawn(board.getField(i,j).getPawnType()) && board.getField(i,j).isPawn()){
                   List<Move> jumps = getPawnJumps(board.getField(i,j));
                   if(!jumps.isEmpty()) availableJumps.addAll(jumps);
                }
            }
        }
        return availableJumps;
    }

     List<Move> getPawnJumps(Field field){
        int x = field.position.x;
        int y = field.position.y;
        int row_limit = board.ROWS-1;
        int column_limit = board.COLS-1;
        List<Move> jumps = new LinkedList<>();

        for (int i = Math.max(0, x - 1); i <= Math.min(x + 1, row_limit); i++) {
            for (int j = Math.max(0, y - 1); j <= Math.min(y + 1, column_limit); j++) {
                if (i != x && j != y) {//only corners
                    Field neighbor = board.getField(i,j);
                    if(canJumpThroughNeighbor(field,neighbor)){
                        Position dst = countJumpDestination(field,neighbor);
                        Move move = new Move(null,field, board.getField(dst.x,dst.y),neighbor,true);
                        jumps.add(move);
                    }
                }
            }
        }
        return jumps;
    }

    List<Move> getPawnMoves(Field field){
        int x = field.position.x;
        int y = field.position.y;
        int row_limit = board.ROWS-1;
        int column_limit = board.COLS-1;
        List<Move> moves = new LinkedList<>();

        for (int i = Math.max(0, x - 1); i <= Math.min(x + 1, row_limit); i++) {
            for (int j = Math.max(0, y - 1); j <= Math.min(y + 1, column_limit); j++) {
                if (i != x && j != y) {//only corners
                    Field neighborField = board.getField(i,j);
                    if(canMove(field,neighborField) && isDestinationAvailable(neighborField.position.x, neighborField.position.y)){
                        Move move = new Move(null,field,neighborField,null,false);
                        moves.add(move);
                    }
                }
            }
        }
        return moves;
    }

    List<Move> getAvailablePawnsMoves(){
        List<Move> availableMoves = new LinkedList<>();
        for(int i=0; i<board.ROWS; i++){
            for(int j=0; j<board.COLS; j++){
                if(player.isMyPawn(board.getField(i,j).getPawnType()) && board.getField(i,j).isPawn()){
                    List<Move> moves = getPawnMoves(board.getField(i,j));
                    if(!moves.isEmpty()){
                        availableMoves.addAll(moves);
                    }
                }
            }
        }
        return availableMoves;

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
        if(!isJumpInProperDirection(src, directionY)) return false;

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

    Position countJumpDestination(Field src, Field neighbor){
        int directionX = neighbor.position.x - src.position.x;
        int directionY = neighbor.position.y - src.position.y;

        int dstX = neighbor.position.x + directionX;
        int dstY = neighbor.position.y + directionY;

        return new Position(dstX,dstY);
    }

    boolean isDestinationAvailable(int dstX, int dstY){
        if(dstX < 0 || dstX > board.ROWS-1) return false;//-1 bo to index w tablicy
        if(dstY < 0 || dstY > board.COLS-1) return false;
        if(board.getField(dstX,dstY).isPawn()) return false;
        return true;
    }

    boolean isNeighborPrey(int preyX, int preyY){
        Field prey = board.getField(preyX,preyY);
        return prey.isPawn() && !player.isMyPawn(prey.getPawnType());
    }

    boolean canMove(Field src, Field dst){
        if(src.getPawnType() == PawnType.WHITE){
            return canWhiteMove(src,dst);
        }else if(src.getPawnType() == PawnType.BLACK){
            return canBlackMove(src,dst);
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

    public void setBoard(Board board) {
        this.board = board;
    }
}
