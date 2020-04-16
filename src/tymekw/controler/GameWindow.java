package controler;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import model.CheckersManager;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Move;
import view.*;

import java.util.List;

public class GameWindow {
    static final int WIDTH = 800;
    static final int HEIGHT = 800;
    private Stage stage;
    private Menu menu;
    private Scene menuScene, gameScene, rulesScene;
    private Game game;
    private Rules rules;
    CheckersManager checkersManager;
    Board board;

    public GameWindow() {
    }

    public void build(){
        StyleManager styleManager = new StyleManager();//only to call constructor
        board = new Board(this);
        checkersManager = new CheckersManager();
        menu = new Menu(this);
        game = new Game(this);
        game.setBoard(board);
        rules = new Rules(this);
        gameScene = new Scene(game.getRoot(), WIDTH, HEIGHT);
        menuScene = new Scene(menu.getRoot(), WIDTH, HEIGHT);
        rulesScene = new Scene(rules.getRoot(), WIDTH, HEIGHT);
        gameScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        menuScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        rulesScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        game.build();
        rules.build();
        stage.setTitle("Warcaby");
    }

    public void start(){
        build();
        stage.setScene(menuScene);
        stage.show();
    }
    public void handleNewGame(){
        checkersManager.restartGame();
        game.reset();
        stage.setScene(gameScene);
    }

    public void handleQuit(){
        stage.close();
    }

    public void handleRules(){
        stage.setScene(rulesScene);
    }

    public void handleMenuButton(){
        stage.setScene(menuScene);
    }

    public void handleResetButton(){
        checkersManager.restartGame();
        game.reset();
    }
    public void setStage(Stage stage){
        this.stage = stage;
    }

    public EventHandler<MouseEvent> getMouseClickHandler(Board board){
        return new MouseClickHandler(this, board, checkersManager);
    }

    public void performMove(List<Move> moves){
        moves.forEach(e->{
            board.showMove(e.getSource().getPosition(), e.getDestination().getPosition());
            if(e.isCaptured()){
                board.removePawn(e.getCaptured().getPosition().x,e.getCaptured().getPosition().y);
            }
        });

    }

}
