package controler;

import model.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class AIPlayer {
    Player whitePlayer, blackPlayer;

    AIPlayer(){

    }

    public AIPlayer(Player whitePlayer, Player blackPlayer) {
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
    }

    List<Move> getMove(Board board, Player player){
        List<Move> bestMove = new LinkedList<>();
        System.out.println("Im in aiplayer get Move");
        MoveAnalyzer moveAnalyzer = new MoveAnalyzer(board, player);
        List<List<Move>> everyPossibleMove = moveAnalyzer.getPossibleMoves();
        Collections.shuffle(everyPossibleMove);
        everyPossibleMove.forEach(e ->{
            e.forEach(System.out::println);
        });


        int depth = 10;
        //double alph = Double.MIN_VALUE;
        double alpha = -10000000;
        System.out.println("alfa start: "+alpha);
        for (List<Move> moves : everyPossibleMove) {
            Board newBoard = board.copyBoard();
            newBoard.performAIMove(moves);
            double newScore = -negMax(newBoard, depth - 1, opponent(player), Double.MIN_VALUE, -alpha);
            System.out.println("Move below, newScore: "+newScore);
            moves.forEach(e ->{
                System.out.println(e);
            });
           // System.out.println("newScore after return: "+newScore+"  alpha: "+alpha);
            if(newScore > alpha){
                alpha = newScore;
                bestMove = moves;
                System.out.println("BEST MOVE MBEST MOVE");
            }
        }
        return bestMove;
    }

    double evaluateBoard(Board board,Player player){
        Field[][] field = board.getFields();
        double playerValue =0;
        double opponent =0;
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                if(field[i][j].isPawn()){
                    if(player.isMyPawn(field[i][j].getPawnType())){
                        if(field[i][j].getPawnType() == PawnType.WHITE_KING || field[i][j].getPawnType() == PawnType.BLACK_KING ){//krol
                            playerValue +=20;
                        }else {
                            playerValue +=10;
                        }
                    }else{
                        if(field[i][j].getPawnType() == PawnType.WHITE_KING || field[i][j].getPawnType() == PawnType.BLACK_KING ){//krol
                            opponent +=20;
                        }else {
                            opponent +=10;
                        }
                    }
                }
            }
        }
        //System.out.println("playerValue-opponent: "+(playerValue-opponent));
        return playerValue-opponent;
    }
    double negMax(Board board, int depth, Player player, double alpha, double beta){
        if(depth == 0){
            return evaluateBoard(board,player);
        }
        MoveAnalyzer moveAnalyzer = new MoveAnalyzer(board, player);
        List<List<Move>> everyPossibleMove = moveAnalyzer.getPossibleMoves();
        for (List<Move> moves : everyPossibleMove) {
            Board newBoard = board.copyBoard();
            newBoard.performAIMove(moves);
            double newScore = -negMax(newBoard, depth - 1, opponent(player), -beta, -alpha);
            if (newScore >= beta) { // alpha-beta cutoff
                return newScore;
            }
            if(newScore > alpha) {
                alpha = newScore;
            }
        }
        return alpha;
    }

/*
    function minimax(node, depth, maximizingPlayer) is
    if depth = 0 or node is a terminal node then
        return the heuristic value of node
    if maximizingPlayer then
    value := −∞
            for each child of node do
    value := max(value, minimax(child, depth − 1, FALSE))
            return value
    else (* minimizing player *)
    value := +∞
            for each child of node do
    value := min(value, minimax(child, depth − 1, TRUE))
            return value

 */
    Player opponent(Player player){
        if(player.getPlayerType() == PlayerType.WHITE_PLAYER){
            return blackPlayer;
        }else{
            return whitePlayer;
        }
    }

}
