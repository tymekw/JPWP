package controler;


import javafx.application.Platform;
import javafx.event.EventHandler;

import javafx.scene.input.MouseEvent;
import model.CheckersManager;
import model.PawnType;
import model.Position;
import view.Board;
import view.Tile;

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
            //if(isJump
            //board.focusPawn(tile);
        }else {
            if(gameWindow.checkersManager.isPossibleMove(src.getPosition(),dst.getPosition())){
                /*if(checkersManager.isAnyJump()){
                    checkersManager.jump(src.getPosition(),dst.getPosition());
                    board.showJump(src,dst);
                }else {
                    checkersManager.move(src.getPosition(), dst.getPosition());
                    board.showMove(src, dst);
                    //System.out.println("board.getFocusedPawn().pawnType == "+board.getFocusedPawn().pawnType);
                }

                 */

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
            }
        }
    }

    void updatePlayer(){
        board.setCurrentPlayer(checkersManager.currentPlayer);
    }

    private void actionWhenNotFocused(Tile tile){
        if(checkersManager.hasAnyAction(tile.getPosition())) {
            //if(checkersManager.isAnyJump())
            tile.focus(tile.pawnType);
            board.setFocusedPawn(tile);
        }
    }
}