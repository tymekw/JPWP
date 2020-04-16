package model;

import java.util.LinkedList;
import java.util.List;

/*
multi_jump(pos start, board board) {
        explore({}, start, board);
        }

        void explore(jump_list so_far, pos start, board board) {
        auto moves = possible_jumps(start, board);
        if (moves.empty()) {
        if (!so_far.empty()) {
        jumps.push_back(so_far);
        }
        } else {
        for (const auto move : moves) {
        board new_board = apply_move(board, start, move);
        jump_list new_so_far = so_far;
        new_so_far.push_back(move);
        explore(new_so_far, move, new_board);
        }
        }
        }
        * */


public class MultiJump {
    List<List<Move>> jumps;
    private Player player;

    MultiJump(Field start, Board board, Player  player){
        this.player = player;
        jumps = new LinkedList<>();
        explore(new LinkedList<Move>(), start,board);
    }

    void explore(List<Move> jumpList, Field start, Board board){
        JumpAnalyzer analyzer = new JumpAnalyzer(board,player);
        List<Move> possiblePawnJumps = analyzer.getPawnJumps(start);

        if(possiblePawnJumps.isEmpty()){
            if(!jumpList.isEmpty()){
                jumps.add(jumpList);
            }
        }else{
            for(Move jump: possiblePawnJumps){
                Board copyBoard = board.copyBoard();
                copyBoard.performMove(jump);
                List<Move> newJumpList = new LinkedList<>();
                newJumpList.addAll(jumpList);
                newJumpList.add(jump);
                Field newStart = copyBoard.getField(jump.getDestination().position.x,jump.getDestination().position.y);
                explore(newJumpList,newStart,copyBoard);
            }
        }
    }

    public List<List<Move>> getJumps() {
        return jumps;
    }
}
