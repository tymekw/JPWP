package controler;


import javafx.application.Platform;
import javafx.event.EventHandler;

import javafx.scene.input.MouseEvent;
import model.*;
import view.Board;
import view.Tile;

import java.util.List;

public class MouseClickHandler implements EventHandler<MouseEvent> {
    GameWindow gameWindow;
    Board board;
    CheckersManager checkersManager;

    public MouseClickHandler(GameWindow gameWindow, Board board, CheckersManager checkersManager) {
        this.checkersManager = checkersManager;
        this.gameWindow = gameWindow;
        this.board = board;
    }

    @Override
    public void handle(MouseEvent event) {
        Tile tile = (Tile)event.getSource();
        if(event.getEventType() == MouseEvent.MOUSE_CLICKED){
            handleClick(tile);
        }
    }

    public void handleClick(Tile tile){
            if(board.isPawnFocused()) {
                actionWhenFocused(tile);
            }else{
                actionWhenNotFocused(tile);
            }
    }

    private void actionWhenFocused(Tile dst){
        Tile src = board.getFocusedPawn();
        if(checkersManager.hasAnyAction(dst.getPosition())) {
            src.removeFocus(src.pawnType);
            dst.focus(dst.pawnType);
            board.setFocusedPawn(dst);
        } else {
            if(gameWindow.checkersManager.isPossibleMove(src.getPosition(),dst.getPosition())){
                checkersManager.move(src.getPosition(),dst.getPosition());
                board.showMove(src,dst);
                if(checkersManager.isPlayerDefeated()){
                    System.out.println("player defeated");
                    Platform.exit();
                }
                if(checkersManager.isCaptured()){
                    Position position = checkersManager.getCaptured();
                    board.removePawn(position.x,position.y);
                }

                if(checkersManager.isPromotion()){
                    dst.promote();
                }
                PawnType pawnType = board.getFocusedPawn().pawnType;
                board.getFocusedPawn().removeFocus(pawnType);
                board.removeFocusedPawn();
                updatePlayer();
                if(CheckersManager.currentPlayer.getPlayerType() == PlayerType.WHITE_PLAYER){
                    AIPlayer aiPlayer = new AIPlayer(checkersManager.whitePlayer,checkersManager.blackPlayer);
                    List<Move> computerMove = aiPlayer.getMove(checkersManager.getBoardCopy(),checkersManager.whitePlayer);
                    checkersManager.performMove(computerMove);
                    System.out.println("computerMove len: "+computerMove.size());
                    gameWindow.performMove(computerMove);
                    if(checkersManager.isPromotion()){
                        Move lastMove = computerMove.get(computerMove.size()-1);
                        Position lastMovePos = lastMove.getDestination().getPosition();
                        board.promotePawn(lastMovePos);
                    }
                    checkersManager.changePlayer();
                }
            }
        }
    }

    void updatePlayer(){
        board.setCurrentPlayer(CheckersManager.currentPlayer);
    }

    private void actionWhenNotFocused(Tile tile){
        if(checkersManager.hasAnyAction(tile.getPosition())) {
            tile.focus(tile.pawnType);
            board.setFocusedPawn(tile);
        }
    }
}