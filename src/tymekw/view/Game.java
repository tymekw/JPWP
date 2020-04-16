package view;

import controler.GameWindow;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

public class Game {
    private BorderPane gameBorder;
    private StackPane root;
    GameWindow gameWindow;
    GridPane grid;
    Board board;

    public Game(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        gameBorder = new BorderPane();
        root = new StackPane();
    }

    public void reset(){
        board.fill();
        GridMaker gridMaker = new GridMaker();
        grid = gridMaker.buildGrid(board);
        gameBorder.setCenter(grid);
    }

    public void build(){
        board.fill();
        GridMaker gridMaker = new GridMaker();
        grid = gridMaker.buildGrid(board);
        gameBorder.setCenter(grid);
        HBox topBar = buildTopBar();
        gameBorder.setTop(topBar);

        root.getChildren().add(gameBorder);
    }

    HBox buildTopBar(){
        HBox hBox = new HBox();
        Button menu = new Button("Menu");
        Button  restart = new Button("Restart");
        menu.setOnMouseClicked(e -> gameWindow.handleMenuButton());
        restart.setOnMouseClicked(e -> gameWindow.handleResetButton());

        hBox.setSpacing(40);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(30,0,0,0));
        hBox.getChildren().addAll(menu, restart);
        return hBox;
    }

    public StackPane getRoot() {
        return root;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}