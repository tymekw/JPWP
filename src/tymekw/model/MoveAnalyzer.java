package model;

import java.util.LinkedList;
import java.util.List;

public class MoveAnalyzer {
    Board board;
    Player player;
    MoveValidator moveValidator;

   public MoveAnalyzer(Board board, Player player){
        this.board = board.copyBoard();
        this.player = player;
    }


    public List<List<Move>> getPossibleMoves(){
        JumpAnalyzer analyzer = new JumpAnalyzer(board,player);
        List<List<Move>> moves = new LinkedList<>();

        if(analyzer.isAnyJump()){
            List<Move> jumpList = analyzer.getAvailableJumps();
           // System.out.println("===========Jest skok, liczba możliwych ;pojedynczych skokow; "+jumpList.size());
            for (Move move : jumpList) {
                MultiJump multiJump = new MultiJump(move.getSource(), board,player);
                List<List<Move>> possiblePawnJumps = multiJump.getJumps();
                //System.out.println("List<List<Move>>.size(): "+possiblePawnJumps.size());
                possiblePawnJumps.forEach(e->{
                    moves.add(e);
                });
                //multiJump.getJumps().forEach(System.out::println);
            }
            //dla każdego skoku wykonujemy go
        }else{
            //analyzer.getAvailablePawnsMoves().forEach(System.out::println);
            analyzer.getAvailablePawnsMoves().forEach(e ->{
                List<Move> simpleMoves = new LinkedList<>();
                simpleMoves.add(e);
                moves.add(simpleMoves);
            });
        }
        //System.out.println("i do return moves, size: "+moves.size());
        return moves;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
